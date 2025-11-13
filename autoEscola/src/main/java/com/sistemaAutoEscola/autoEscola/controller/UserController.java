package com.sistemaAutoEscola.autoescola.controller; // Note que ajustei o pacote para 'autoescola.controller'

import com.sistemaAutoEscola.autoescola.domain.User;
import com.sistemaAutoEscola.autoescola.dto.request.CadastroRequest; // ⬅️ IMPORT CORRETO DO DTO
import com.sistemaAutoEscola.autoescola.dto.response.UserResponse;
import com.sistemaAutoEscola.autoescola.service.UserService;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios") // Mapeamento principal
public class UserController {
    @Autowired
    private com.sistemaAutoEscola.autoEscola.repository.InstrutorRepository instrutorRepository;

    @Autowired
    private UserService userService;

    /**
     * Endpoint para criar um novo usuário (Cadastro).
     * Mapeamento: POST /api/usuarios/cadastro
     */
    @PostMapping("/cadastro")
    // Como é um cadastro, esta rota geralmente é pública (sem @PreAuthorize)
    public ResponseEntity<UserResponse> criarUsuario(@RequestBody CadastroRequest request) {

        try {
                // 1. O SERVICE retorna a ENTIDADE User
                User usuarioCriado = userService.criarNovoUsuario(request);

                // 2. Mapeamento: Converte a Entidade User para o DTO UserResponse
                UserResponse responseDto = new UserResponse(
                    usuarioCriado.getId(),
                    usuarioCriado.getUsername(),
                    usuarioCriado.getEmail(),
                    usuarioCriado.getRoles().stream()
                        .map(r -> r.getAuthority()) // Pega os nomes das roles
                        .collect(Collectors.toSet())
                );

                // 3. Retorna 200 OK com o DTO
                return ResponseEntity.ok(responseDto); 

            } catch (RuntimeException ex) {
                // ...
                return ResponseEntity.badRequest().body(null); 
            }
    }
    
    // Exemplo de uma rota simples e protegida:
    @GetMapping("/teste-acesso")
    @PreAuthorize("hasAnyRole('ALUNO', 'INSTRUTOR', 'ADMIN')")
    public ResponseEntity<String> testeAcesso() {
        return ResponseEntity.ok("Acesso permitido: Esta rota está protegida.");
    }
    
    @GetMapping("/usuarios")
    @PreAuthorize("hasAnyRole('ALUNO', 'INSTRUTOR', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> listarTodosUsuarios() {

        List<User> users = userService.findAllUsers();

        // Mapeia List<User> para List<UserResponse> DTO
        List<UserResponse> response = users.stream()
                .map(this::convertToDto) // Usa o método auxiliar abaixo
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/instrutoress")
    @PreAuthorize("hasAnyRole('ALUNO', 'INSTRUTOR', 'ADMIN')") // Admin ou Instrutor pode ver
    public ResponseEntity<List<UserResponse>> listarInstrutores() {

        List<User> instructors = userService.findInstructors();
        List<UserResponse> response = instructors.stream()
            .map(user -> {
                Long instrutorId = null;
                // Buscar o Instrutor pelo User
                Optional<com.sistemaAutoEscola.autoescola.domain.Instrutor> instrutorOpt = instrutorRepository.findByUser(user);
                if (instrutorOpt.isPresent()) {
                    instrutorId = instrutorOpt.get().getId();
                }
                return new UserResponse(
                    instrutorId, // id do instrutor
                    user.getUsername(),
                    user.getEmail(),
                    user.getRoles().stream().map(r -> r.getAuthority()).collect(Collectors.toSet())
                );
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // ------------------------------------------------------------------------

    /**
     * Método auxiliar para mapear a Entidade User para o DTO UserResponse.
     * Centraliza a lógica de mapeamento para evitar repetição.
     */
    private UserResponse convertToDto(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRoles().stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toSet())
        );
    }
}