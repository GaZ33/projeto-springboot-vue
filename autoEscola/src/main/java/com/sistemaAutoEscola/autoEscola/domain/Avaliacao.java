package com.sistemaAutoEscola.autoescola.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Avaliacao implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id;

    @Column(name = "nota_avaliacao", nullable = false)
    private int notaAvaliacao;

    @Column(name = "tipo_avaliacao", nullable = false, length = 15)
    private String tipoAvaliacao;

    @Column(name = "desc_avaliacao", length = 100)
    private String descAvaliacao;

    public Long getId() {
        return id;
    }

    public int getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public String getDescAvaliacao() {
        return descAvaliacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotaAvaliacao(int notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public void setDescAvaliacao(String descAvaliacao) {
        this.descAvaliacao = descAvaliacao;
    }
    
    
}
