package com.chatbot.financeiro.user;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

public class User {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Users")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column (name = "TipoPessoa", nullable = false)
    private TipoPessoa tipoPessoa;

    @Column (name = "NomeOuRazao", nullable = false, length = 70)
    private String nomeOuRazao;
    
    @Column (name = "Documento", nullable = false, length = 18, unique = true)
    private String documento;
    
    @Column (name = "Email", nullable = false, length = 100, unique = true)    
    private String email;
    
    @Column (name="DataNascimento")
    private LocalDate dataNascimento;
    
    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;
    
    @Column(name = "renda_ou_faturamento", precision = 12, scale = 2)
    private BigDecimal rendaOuFaturamento;
    
    @Column(name = "cep", length = 9)
    private String cep;
    
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist // Método chamado antes de persistir a entidade no banco de dados
    protected void onCreate() { 
        if (this.criadoEm == null) { // Se a data de criação não estiver definida, define como a data atual
            this.criadoEm = LocalDateTime.now();
        }
        if (this.ativo == null) { // Se o status ativo não estiver definido, define como true
            this.ativo = true;
        }
    }

    public User() {
    }

    public User(Integer id, TipoPessoa tipoPessoa, String nomeOuRazao, String documento, String email, LocalDate dataNascimento, String senhaHash, BigDecimal rendaOuFaturamento, String cep, Boolean ativo, LocalDateTime criadoEm) {
        this.id = id;
        this.tipoPessoa = tipoPessoa;
        this.nomeOuRazao = nomeOuRazao;
        this.documento = documento;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.senhaHash = senhaHash;
        this.rendaOuFaturamento = rendaOuFaturamento;
        this.cep = cep;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getNomeOuRazao() {
        return nomeOuRazao;
    }

    public void setNomeOuRazao(String nomeOuRazao) {
        this.nomeOuRazao = nomeOuRazao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public BigDecimal getRendaOuFaturamento() {
        return rendaOuFaturamento;
    }

    public void setRendaOuFaturamento(BigDecimal rendaOuFaturamento) {
        this.rendaOuFaturamento = rendaOuFaturamento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
    
}