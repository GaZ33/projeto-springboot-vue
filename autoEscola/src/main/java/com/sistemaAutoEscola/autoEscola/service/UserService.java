package com.sistemaAutoEscola.autoescola.service;

import com.sistemaAutoEscola.autoescola.domain.User;
import com.sistemaAutoEscola.autoescola.domain.Role;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.sistemaAutoEscola.autoescola.repository.UserRepository;
import com.sistemaAutoEscola.autoescola.repository.RoleRepository;
import com.sistemaAutoEscola.autoescola.dto.request.CadastroRequest; // ⬅️ IMPORT CORRETO DO DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import para garantir o carregamento LAZY

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository; 
    
    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Transactional // Garante que as operações de banco (Role, User) estejam na mesma sessão
    public User criarNovoUsuario(CadastroRequest request) {
        
        // 1. Validação (Opcional, mas Recomendado): Verifica se o Username já existe
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Nome de usuário já está em uso.");
        }
        
        // 2. Hashear a Senha (Segurança!)
        String senhaHasheada = passwordEncoder.encode(request.password());
        
        // 3. Mapear tipoUsuario (ID) para Role
        // O tipoUsuario (1, 2, 3...) deve corresponder ao ID da Role no banco
        Role role = roleRepository.findById(request.tipoUsuario())
                .orElseThrow(() -> new RuntimeException("Role ID " + request.tipoUsuario() + " não encontrada no banco."));
        
        // 4. Criar a Entidade User
        User novoUser = new User();
        
        // Crie um email usando o username (simples)
        String email = request.email();
        
        novoUser.setUsername(request.username());
        novoUser.setEmail(email); 
        novoUser.setPassword(senhaHasheada);
        novoUser.setEnabled(true);
        novoUser.setRoles(Set.of(role)); // Adiciona a role mapeada
        
        // 5. Salvar e retornar
        User usuarioSalvo = userRepository.save(novoUser);

        // 🚨 CRUCIAL: Forçar o Lazy Loading antes de retornar (para o Controller)
        // Isso impede a LazyInitializationException ao serializar as roles
        usuarioSalvo.getRoles().size(); 

        return usuarioSalvo;
    }
    
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        // Busca todos os usuários. O @Transactional garante que as roles LAZY sejam carregadas.
        List<User> users = userRepository.findAll();

        // Força o carregamento das roles para evitar LazyInitializationException no Controller
        users.forEach(user -> user.getRoles().size()); 

        return users;
    }
    
@Transactional(readOnly = true)
public List<User> findInstructors() {
    
    // Busca todos os usuários
    List<User> allUsers = userRepository.findAll(); 
    
    // Define a string exata que está no banco de dados
    final String targetRole = "INSTRUTOR";
    
    // Filtra usuários onde pelo menos uma role corresponde à string 'INSTRUTOR'
    List<User> instructors = allUsers.stream()
            .filter(user -> user.getRoles().stream()
                // 🚨 CORREÇÃO: Compara diretamente com o nome da Role do banco
                .anyMatch(role -> role.getName().equals(targetRole))) // Usando .getName() para obter o valor literal do banco
            .peek(user -> user.getRoles().size()) // Força o Lazy Loading
            .collect(Collectors.toList());
            
    return instructors;
}
}