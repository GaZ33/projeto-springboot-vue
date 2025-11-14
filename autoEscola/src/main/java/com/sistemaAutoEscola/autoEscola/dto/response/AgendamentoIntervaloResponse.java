package com.sistemaAutoEscola.autoescola.dto.response;

import com.sistemaAutoEscola.autoescola.domain.AgendamentoAula;
import lombok.Data;

@Data
public class AgendamentoIntervaloResponse {
    private Long id;
    private String data;
    private String hora;
    private String aluno;
    private String instrutor;
    private String situacao;

    public AgendamentoIntervaloResponse(AgendamentoAula agendamento) {
        this.id = agendamento.getId();
        this.data = agendamento.getDataAgendamento() != null ? agendamento.getDataAgendamento().toLocalDate().toString() : "";
        this.hora = agendamento.getDataAgendamento() != null ? agendamento.getDataAgendamento().toLocalTime().toString() : "";
        this.aluno = agendamento.getAluno() != null ? agendamento.getAluno().getPnomeAluno() + " " + agendamento.getAluno().getSnomeAluno() : "";
        this.instrutor = agendamento.getInstrutor() != null ? agendamento.getInstrutor().getPnomeInstrutor() + " " + agendamento.getInstrutor().getSnomeInstrutor() : "";
        this.situacao = agendamento.getSituacaoAgendamento() != null ? agendamento.getSituacaoAgendamento().name() : "";
    }
}
