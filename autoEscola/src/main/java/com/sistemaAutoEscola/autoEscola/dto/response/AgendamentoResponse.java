package com.sistemaAutoEscola.autoescola.dto.response;

import java.time.LocalDateTime;

/**
 * DTO de Resposta para Agendamento.
 * Contém apenas os dados que o frontend deve receber após o cadastro.
 */
public record AgendamentoResponse(
    // 1. Chave primária do agendamento
    Long id,
    
    // 2. Data e hora da aula
    LocalDateTime dataAgendamento,
    
    // 3. Situação do agendamento (ex: "AGENDADO", "CONCLUIDO")
    String situacao,
    
    // 4. ID do Aluno envolvido
    Long alunoId,
    
    // 5. ID do Instrutor envolvido
    Long instrutorId,
    
    // 6. Username do Aluno
    String alunoUsername,
    
    // 7. Username do Instrutor
    String instrutorUsername
) {
    // O corpo está vazio, pois o 'record' gera automaticamente o construtor, getters e toString.
}