/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaAutoEscola.autoEscola.dto.request;

/**
 *
 * @author gasin
 */
public class AlunoRequest {
    private Long usuarioId;
    private String cpf;
    private String primeiroNome;
    private String sobrenome;
    private String celular;
    private String dataNascimento; // ou LocalDate se preferir
    private String bairro;
    private String rua;
    // getters e setters

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCelular() {
        return celular;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }
    
}
