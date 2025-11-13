package com.sistemaAutoEscola.autoescola.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import lombok.Setter;
import lombok.Getter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "aluno")
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long id;

    public Long getId() {
        return id;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public LocalDate getNascimentoAluno() {
        return nascimentoAluno;
    }

    public LocalDate getDataRegistroAluno() {
        return dataRegistroAluno;
    }

    public String getCelular() {
        return celular;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public String getPnomeAluno() {
        return pnomeAluno;
    }

    public String getSnomeAluno() {
        return snomeAluno;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public void setNascimentoAluno(LocalDate nascimentoAluno) {
        this.nascimentoAluno = nascimentoAluno;
    }

    public void setDataRegistroAluno(LocalDate dataRegistroAluno) {
        this.dataRegistroAluno = dataRegistroAluno;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setPnomeAluno(String pnomeAluno) {
        this.pnomeAluno = pnomeAluno;
    }

    public void setSnomeAluno(String snomeAluno) {
        this.snomeAluno = snomeAluno;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCnh(Cnh cnh) {
        this.cnh = cnh;
    }

    public void setExames(List<Exame> exames) {
        this.exames = exames;
    }

    public void setAgendamentos(List<AgendamentoAula> agendamentos) {
        this.agendamentos = agendamentos;
    }

    public Cnh getCnh() {
        return cnh;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public List<AgendamentoAula> getAgendamentos() {
        return agendamentos;
    }

    @Column(name = "cpf_aluno", nullable = false, unique = true, length = 11)
    private String cpfAluno;

    @Column(name = "nascimento_aluno")
    private LocalDate nascimentoAluno;

    @Column(name = "data_registro_aluno", nullable = false)
    private LocalDate dataRegistroAluno;

    @Column(nullable = false, length = 15)
    private String celular;

    @Column(length = 25)
    private String bairro;

    @Column(length = 25)
    private String rua;

    @Column(name = "pnome_aluno", nullable = false, length = 15)
    private String pnomeAluno;

    @Column(name = "snome_aluno", nullable = false, length = 25)
    private String snomeAluno;

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user", nullable = false)
    private User user;
    
    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL)
 
    private Cnh cnh;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exame> exames;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendamentoAula> agendamentos;
}

