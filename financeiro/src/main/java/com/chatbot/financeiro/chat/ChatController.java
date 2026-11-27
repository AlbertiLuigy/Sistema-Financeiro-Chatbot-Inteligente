package com.chatbot.financeiro.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    public record ChatRequest(String sessaoId, String conteudo) {}

    @PostMapping("/mensagens")
    public ResponseEntity<?> enviarMensagem(
            @PathVariable Integer userId,
            @RequestBody ChatRequest request
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(chatService.enviarMensagem(userId, request.sessaoId(), request.conteudo()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sessao/{sessaoId}")
    public ResponseEntity<?> buscarHistorico(
            @PathVariable Integer userId,
            @PathVariable String sessaoId
    ) {
        try {
            return ResponseEntity.ok(chatService.buscarHistoricoSessao(userId, sessaoId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarPorUsuario(@PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(chatService.listarPorUsuario(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
