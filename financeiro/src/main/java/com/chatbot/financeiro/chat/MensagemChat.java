package com.chatbot.financeiro.chat;

import java.time.LocalDateTime;

import com.chatbot.financeiro.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity 
@Table(name = "mensagens_chat")
public class MensagemChat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmensagens_chat")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_Users", nullable = false)
    private User user;

    @Column(name = "sessao_id", nullable = false, length = 45)
    private String sessaoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "remetente", nullable = false)
    private RemetenteMensagem remetente;

    @Column(name = "conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(name = "fonte_resposta")
    private FonteResposta fonteResposta;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        if (this.criadoEm == null) {
            this.criadoEm = LocalDateTime.now();
        }
        if (this.remetente == RemetenteMensagem.USUARIO && this.fonteResposta == null) {
            this.fonteResposta = FonteResposta.NENHUMA;
        }
    }

    public MensagemChat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(String sessaoId) {
        this.sessaoId = sessaoId;
    }

    public RemetenteMensagem getRemetente() {
        return remetente;
    }

    public void setRemetente(RemetenteMensagem remetente) {
        this.remetente = remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public FonteResposta getFonteResposta() {
        return fonteResposta;
    }

    public void setFonteResposta(FonteResposta fonteResposta) {
        this.fonteResposta = fonteResposta;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
