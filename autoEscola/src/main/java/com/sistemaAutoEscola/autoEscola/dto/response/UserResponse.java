package com.sistemaAutoEscola.autoescola.dto.response;

import java.util.Set;

// O DTO de saída: Contém APENAS os dados que queremos expor
public record UserResponse(
    Long id,
    String username,
    String email,
    Set<String> roles // Retorna apenas o nome da Role
) {}