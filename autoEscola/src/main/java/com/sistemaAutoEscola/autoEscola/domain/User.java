package com.sistemaAutoEscola.autoescola.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id; 

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }


    /**
     * Mapeia as Roles para as Authorities que o Spring Security usa.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getAuthority()))
                .collect(Collectors.toSet());
    }

  
    /**
     * CORREÇÃO: Implementação manual do getPassword().
     * Isso resolve o bug onde o AuthenticationProvider recebia 'null'
     * do método gerado pelo Lombok.
     */
    @Override
    public String getPassword() {
        return this.password;
    }
    

    @Override
    public String getUsername() {
        return username; // O nome de usuário para autenticação
    }

    // Métodos abaixo devem retornar true (ou usar lógica específica, se necessário)
    @Override
    public boolean isAccountNonExpired() { 
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { 
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { 
        return true;
    }

    @Override
    public boolean isEnabled() { 
        return this.enabled; // Usa o campo 'enabled'
    }
}