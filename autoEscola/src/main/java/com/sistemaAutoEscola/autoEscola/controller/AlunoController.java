/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemaAutoEscola.autoescola.controller;

import com.sistemaAutoEscola.autoEscola.dto.request.AlunoRequest;
import com.sistemaAutoEscola.autoescola.domain.Aluno;
import com.sistemaAutoEscola.autoescola.domain.Cnh;
import com.sistemaAutoEscola.autoescola.domain.Exame;
import com.sistemaAutoEscola.autoescola.service.CnhServiceImpl;
import com.sistemaAutoEscola.autoescola.service.ExameServiceImpl;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/aluno")
public class AlunoController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ExameServiceImpl exameService;

    @Autowired
    private CnhServiceImpl cnhService;

    // Endpoint para o ALUNO ver seu histórico de exames
    @GetMapping("/meus/exames")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<List<Exame>> getMeusExames() {
        // O service buscará as entidades de Exame associadas ao usuário autenticado
        List<Exame> exames = exameService.findExamesByAlunoLogado();
        return ResponseEntity.ok(exames);
    }

    // Endpoint para o ALUNO ver a situação da sua CNH
    // Retornando uma lista, pois um aluno pode teoricamente ter mais de uma CNH (A, B, etc.)
    @GetMapping("/meus/cnh")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<List<Cnh>> getMinhaCnh() {
        // O service buscará as entidades de Cnh associadas ao usuário autenticado
        List<Cnh> cnhs = cnhService.findCnhByAlunoLogado();
        return ResponseEntity.ok(cnhs);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarAluno(@RequestBody AlunoRequest request) {
        Aluno aluno = new Aluno();
        // Preencha os campos do aluno com os dados do request
        aluno.setCpfAluno(request.getCpf());
        aluno.setPnomeAluno(request.getPrimeiroNome());
        aluno.setSnomeAluno(request.getSobrenome());
        aluno.setCelular(request.getCelular());
        aluno.setNascimentoAluno(LocalDate.parse(request.getDataNascimento())); // ajuste tipo se necessário
        aluno.setBairro(request.getBairro());
        aluno.setRua(request.getRua());
        // Relacione o usuário (busque pelo id)
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        aluno.setUsuario(usuario);

        alunoService.saveAluno(aluno);
        return ResponseEntity.ok().build();

    }
}
