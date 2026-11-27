package com.chatbot.financeiro.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemChatRepository extends JpaRepository<MensagemChat, Integer> {

    List<MensagemChat> findByUserIdAndSessaoIdOrderByCriadoEmAsc(Integer userId, String sessaoId);

    List<MensagemChat> findByUserIdOrderByCriadoEmDesc(Integer userId);
}