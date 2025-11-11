package com.sistemaAutoEscola.autoescola.controller; // Note que ajustei o pacote para 'autoescola.controller'

import com.sistemaAutoEscola.autoescola.domain.User;
import com.sistemaAutoEscola.autoescola.dto.request.CadastroRequest; // ⬅️ IMPORT CORRETO DO DTO
import com.sistemaAutoEscola.autoescola.dto.response.UserResponse;
import com.sistemaAutoEscola.autoescola.service.UserService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios") // Mapeamento principal
public class UserController {

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
}