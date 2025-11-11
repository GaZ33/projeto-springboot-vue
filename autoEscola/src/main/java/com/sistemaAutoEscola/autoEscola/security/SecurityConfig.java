package com.sistemaAutoEscola.autoescola.security;

import com.sistemaAutoEscola.autoescola.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Adicionado para configuração de CORS:
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // =========================================================================
    // 1. CONFIGURAÇÃO DE CORS (CRUCIAL PARA O VUE.JS NA PORTA 8081)
    // =========================================================================
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite que seu frontend Vue.js acesse:
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081")); 
        
        // Define os métodos permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permite todos os cabeçalhos (incluindo 'Authorization')
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        
        // Essencial para lidar com credenciais (como o token JWT)
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // =========================================================================
    // 2. CONFIGURAÇÃO DE AUTENTICAÇÃO E SENHAS
    // =========================================================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(authenticationProvider());
        return authBuilder.build();
    }

    // =========================================================================
    // 3. CADEIA DE FILTROS DE SEGURANÇA
    // =========================================================================

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 🚨 HABILITA O CORS USANDO A CONFIGURAÇÃO ACIMA
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            
            // Desabilita CSRF (necessário para APIs REST)
            .csrf(csrf -> csrf.disable())
            
            // Define o estado como SEM ESTADO (STATELESS) para JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                // Permite acesso público ao endpoint de login
                .requestMatchers("/api/auth/**").permitAll() 
                
                // Exige autenticação (JWT) para todo o resto
                .anyRequest().authenticated()
            )
            
            // Garante que o provedor customizado seja usado
            .authenticationProvider(authenticationProvider())
            
            // Adiciona o filtro JWT antes do filtro padrão de autenticação
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}