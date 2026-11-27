package com.chatbot.financeiro.chat;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.chatbot.financeiro.user.User;
import com.chatbot.financeiro.user.UserService;

@Service 
public class ChatService {
    
    private final MensagemChatRepository mensagemChatRepository;
    private final UserService userService;

    public ChatService(MensagemChatRepository mensagemChatRepository, UserService userService) {
        this.mensagemChatRepository = mensagemChatRepository;
        this.userService = userService;
    }

    public MensagemChat enviarMensagem(Integer userId, String sessaoId, String conteudo) {
        User user = userService.buscarPorId(userId);

        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("O conteúdo da mensagem não pode estar vazio.");
        }

        String sessaoFinal = (sessaoId == null || sessaoId.isBlank())
                ? UUID.randomUUID().toString()
                : sessaoId;

        // 1. Salva mensagem do usuário
        MensagemChat msgUsuario = new MensagemChat();
        msgUsuario.setUser(user);
        msgUsuario.setSessaoId(sessaoFinal);
        msgUsuario.setRemetente(RemetenteMensagem.USUARIO);
        msgUsuario.setConteudo(conteudo.trim());
        msgUsuario.setFonteResposta(FonteResposta.NENHUMA);
        mensagemChatRepository.save(msgUsuario);

        // 2. Gera resposta inicial (será substituída/enriquecida com Spring AI + RAG + Tools)
        String respostaTexto = "Recebi sua mensagem: \"" + conteudo.trim()
                + "\". A base de dados e o histórico já estão ativos; em breve conecto o motor de IA com o manual e seus dados financeiros.";

        // 3. Salva resposta do bot
        MensagemChat msgBot = new MensagemChat();
        msgBot.setUser(user);
        msgBot.setSessaoId(sessaoFinal);
        msgBot.setRemetente(RemetenteMensagem.BOT);
        msgBot.setConteudo(respostaTexto);
        msgBot.setFonteResposta(FonteResposta.REGRA);

        return mensagemChatRepository.save(msgBot);
    }

    public List<MensagemChat> buscarHistoricoSessao(Integer userId, String sessaoId) {
        userService.buscarPorId(userId);
        return mensagemChatRepository.findByUserIdAndSessaoIdOrderByCriadoEmAsc(userId, sessaoId);
    }

    public List<MensagemChat> listarPorUsuario(Integer userId) {
        userService.buscarPorId(userId);
        return mensagemChatRepository.findByUserIdOrderByCriadoEmDesc(userId);
    }
}
