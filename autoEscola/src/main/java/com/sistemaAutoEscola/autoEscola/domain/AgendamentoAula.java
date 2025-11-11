package com.sistemaAutoEscola.autoescola.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.SituacaoAgendamento;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agendamento_aula")
public class AgendamentoAula implements Serializable {

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public void setSituacaoAgendamento(SituacaoAgendamento situacaoAgendamento) {
        this.situacaoAgendamento = situacaoAgendamento;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long id;

    @Column(name = "data_agendamento", nullable = false)
    private LocalDateTime dataAgendamento; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_agendamento", nullable = false)
    private SituacaoAgendamento situacaoAgendamento;

    @JsonIgnore    
    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Instrutor instrutor;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @OneToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }

    public SituacaoAgendamento getSituacaoAgendamento() {
        return situacaoAgendamento;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }
    
    
}

