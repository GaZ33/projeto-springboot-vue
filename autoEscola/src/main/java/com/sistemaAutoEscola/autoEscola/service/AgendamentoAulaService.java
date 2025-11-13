package com.sistemaAutoEscola.autoescola.service;

import com.sistemaAutoEscola.autoescola.domain.AgendamentoAula;
import com.sistemaAutoEscola.autoescola.domain.Avaliacao;
import com.sistemaAutoEscola.autoescola.dto.request.AgendamentoRequest; // Adicionado para a rota de cadastro
import enums.SituacaoAgendamento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoAulaService {

    // ----------------------------------------------------------------------
    // MÉTODOS DE BUSCA E VISUALIZAÇÃO
    // ----------------------------------------------------------------------

    /** Busca os agendamentos do aluno atualmente autenticado. */
    List<AgendamentoAula> findAgendamentosByAlunoLogado();
    
    /** Busca os agendamentos do instrutor atualmente autenticado. */
    List<AgendamentoAula> findAgendamentosByInstrutorLogado();

    /** Busca agendamentos entre duas datas/horas. */
    List<AgendamentoAula> buscarPorIntervalo(LocalDateTime inicio, LocalDateTime fim);

    /** Busca agendamentos em um único dia. (Sobrecarga) */
    List<AgendamentoAula> buscarPorIntervalo(LocalDate data);
    
    /** Busca agendamentos pelo CPF do aluno (apenas para instrutores/admins). */
    List<AgendamentoAula> buscarAgendamentosPorAlunoPorCpf(String cpf);
    
    /** Busca agendamentos pelo nome e sobrenome do aluno (apenas para instrutores/admins). */
    List<AgendamentoAula> buscarAgendamentosPorAlunoPorNomes(String pnome, String snome);


    // ----------------------------------------------------------------------
    // MÉTODOS DE AÇÃO / TRANSAÇÃO
    // ----------------------------------------------------------------------
    
    /** 🚨 CRIA UM NOVO AGENDAMENTO A PARTIR DO DTO. */
    AgendamentoAula criarNovoAgendamento(AgendamentoRequest request);

    /** Conclui um agendamento e associa uma avaliação. */
    AgendamentoAula concluirAgendamento(Long agendamentoId, Avaliacao avaliacao);

    /** Altera o status de um agendamento (ex: CONCLUIDO, CANCELADO). */
    AgendamentoAula alterarStatus(long id, SituacaoAgendamento statusAgendamento);
}