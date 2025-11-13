package com.sistemaAutoEscola.autoescola.controller;

import com.sistemaAutoEscola.autoescola.domain.AgendamentoAula;
import com.sistemaAutoEscola.autoescola.domain.Avaliacao;
import com.sistemaAutoEscola.autoescola.dto.request.AgendamentoRequest;
import com.sistemaAutoEscola.autoescola.dto.response.AgendamentoResponse;
import com.sistemaAutoEscola.autoescola.service.AgendamentoAulaServiceImpl;
import enums.SituacaoAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoAulaController {

    private final AgendamentoAulaServiceImpl agendamentoService;

    @Autowired
    public AgendamentoAulaController(AgendamentoAulaServiceImpl agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    // ======================== CONSULTAS ===============================

    /** ALUNO - Ver suas próprias aulas */
    @GetMapping("/aluno/meus")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<List<AgendamentoAula>> getMinhasAulasAluno() {
        List<AgendamentoAula> agendamentos = agendamentoService.findAgendamentosByAlunoLogado();
        return ResponseEntity.ok(agendamentos);
    }

    /** INSTRUTOR - Ver suas próprias aulas */
    @GetMapping("/instrutor/meus")
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<List<AgendamentoAula>> getMinhasAulasInstrutor() {
        List<AgendamentoAula> agendamentos = agendamentoService.findAgendamentosByInstrutorLogado();
        return ResponseEntity.ok(agendamentos);
    }

    /** Buscar agendamentos entre duas datas */
    @GetMapping("/intervalo")
    @PreAuthorize("hasAnyRole('INSTRUTOR')")
    public ResponseEntity<List<AgendamentoAula>> buscarPorIntervalo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {

        List<AgendamentoAula> agendamentos = agendamentoService.buscarPorIntervalo(inicio, fim);
        return ResponseEntity.ok(agendamentos);
    }

    /** Buscar agendamentos de um único dia */
    @GetMapping("/dia")
    @PreAuthorize("hasAnyRole('INSTRUTOR')")
    public ResponseEntity<List<AgendamentoAula>> buscarPorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<AgendamentoAula> agendamentos = agendamentoService.buscarPorIntervalo(data);
        return ResponseEntity.ok(agendamentos);
    }

    /** 🔍 Buscar agendamentos pelo nome e sobrenome do aluno */
    @GetMapping("/aluno/nome")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ADMIN')")
    public ResponseEntity<List<AgendamentoAula>> buscarPorNomeAluno(
            @RequestParam("pnome") String pnome,
            @RequestParam("snome") String snome) {

        List<AgendamentoAula> agendamentos = agendamentoService.buscarAgendamentosPorAlunoPorNomes(pnome, snome);
        return ResponseEntity.ok(agendamentos);
    }

    /** 🔍 Buscar agendamentos pelo CPF do aluno */
    @GetMapping("/aluno/cpf")
    @PreAuthorize("hasAnyRole('INSTRUTOR')")
    public ResponseEntity<List<AgendamentoAula>> buscarPorCpfAluno(
            @RequestParam("cpf") String cpfAluno) {

        List<AgendamentoAula> agendamentos = agendamentoService.buscarAgendamentosPorAlunoPorCpf(cpfAluno);
        return ResponseEntity.ok(agendamentos);
    }

    // ======================== AÇÕES ===============================

    /** INSTRUTOR - Concluir agendamento com avaliação */
    @PutMapping("/{id}/concluir")
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<AgendamentoAula> concluirAgendamento(
            @PathVariable Long id,
            @RequestBody Avaliacao avaliacao) {

        AgendamentoAula concluido = agendamentoService.concluirAgendamento(id, avaliacao);
        return ResponseEntity.ok(concluido);
    }

    /** INSTRUTOR - Alterar status (CONCLUIDO, CANCELADO, AUSENTE, etc.) */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<AgendamentoAula> alterarStatus(
            @PathVariable Long id,
            @RequestParam SituacaoAgendamento status) {

        AgendamentoAula atualizado = agendamentoService.alterarStatus(id, status);
        return ResponseEntity.ok(atualizado);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ALUNO')") // Permissão para quem pode agendar
    public ResponseEntity<AgendamentoResponse> criarAgendamento(@RequestBody AgendamentoRequest request) {

        try {
            // Chama o serviço para criar e salvar
            AgendamentoAula novoAgendamento = agendamentoService.criarNovoAgendamento(request);

            // Mapeia a entidade para um DTO de resposta (recomendado)
            AgendamentoResponse responseDto = mapToResponse(novoAgendamento);

            return ResponseEntity.ok(responseDto);

        } catch (RuntimeException ex) {
            // Captura erros como IDs não encontrados, horário indisponível, etc.
            return ResponseEntity.badRequest().body(null); 
        }
    }
    
    // Método auxiliar (você precisará criar o AgendamentoResponse DTO)
    private AgendamentoResponse mapToResponse(AgendamentoAula agendamento) {
        // Implemente a lógica de mapeamento aqui (ID, data, status, etc.)
        return new AgendamentoResponse(
            agendamento.getId(), 
            agendamento.getDataAgendamento(),
            agendamento.getSituacaoAgendamento().toString(),
            agendamento.getAluno().getId(), // Use o getter correto para o ID do Aluno
            agendamento.getInstrutor().getId(),
            agendamento.getAluno().getUser().getUsername(), 
            agendamento.getInstrutor().getUser().getUsername()
        );
    }
}
