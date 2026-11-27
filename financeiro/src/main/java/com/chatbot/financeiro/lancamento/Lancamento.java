package com.chatbot.financeiro.lancamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.chatbot.financeiro.categoria.Categoria;
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
@Table(name = "lancamentos")
public class Lancamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlancamentos")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_Users", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Categorias_idCategorias", nullable = false)
    private Categoria categoria;

    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoLancamento tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusLancamento status;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "recorrente", nullable = false)
    private Boolean recorrente = false;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        if (this.criadoEm == null) {
            this.criadoEm = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = StatusLancamento.PENDENTE;
        }
        if (this.recorrente == null) {
            this.recorrente = false;
        }
    }

    public Lancamento() {
    }

    public Lancamento(Integer id, User user, Categoria categoria, String descricao, BigDecimal valor, TipoLancamento tipo, StatusLancamento status, LocalDate dataVencimento, LocalDate dataPagamento, Boolean recorrente, LocalDateTime criadoEm) {
        this.id = id;
        this.user = user;
        this.categoria = categoria;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.status = status;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.recorrente = recorrente;
        this.criadoEm = criadoEm;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public StatusLancamento getStatus() {
        return status;
    }

    public void setStatus(StatusLancamento status) {
        this.status = status;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Boolean getRecorrente() {
        return recorrente;
    }

    public void setRecorrente(Boolean recorrente) {
        this.recorrente = recorrente;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    
}
