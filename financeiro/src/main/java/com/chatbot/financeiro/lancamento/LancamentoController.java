package com.chatbot.financeiro.lancamento;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity<?> criar(@PathVariable Integer userId, @RequestBody LancamentoRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoService.criar(userId, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarPorUsuario(
            @PathVariable Integer userId,
            @RequestParam(required = false) TipoLancamento tipo,
            @RequestParam(required = false) StatusLancamento status
    ) {
        try {
            return ResponseEntity.ok(lancamentoService.listarPorUsuario(userId, tipo, status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/periodo")
    public ResponseEntity<?> listarPorPeriodo(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        try {
            return ResponseEntity.ok(lancamentoService.listarPorPeriodo(userId, inicio, fim));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/resumo")
    public ResponseEntity<?> resumoPeriodo(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        try {
            return ResponseEntity.ok(lancamentoService.calcularResumoPeriodo(userId, inicio, fim));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer userId, @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(lancamentoService.buscarPorId(userId, id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Integer userId,
            @PathVariable Integer id,
            @RequestBody LancamentoRequest request
    ) {
        try {
            return ResponseEntity.ok(lancamentoService.atualizar(userId, id, request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer userId, @PathVariable Integer id) {
        try {
            lancamentoService.excluir(userId, id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
