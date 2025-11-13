package com.sistemaAutoEscola.autoescola.dto.request;

import java.time.LocalDateTime;

// O DTO mapeia o payload do Vue.js
public record AgendamentoRequest(
    Long alunoId,
    Long instrutorId,
    LocalDateTime datetime // Mapeia a string ISO 8601 (AAAA-MM-DDT00:00:00)
) {
    // O Spring Boot (Jackson) consegue converter automaticamente a string datetime ISO 
    // em um objeto LocalDateTime, desde que a formatação esteja correta (como no seu Vue).
}