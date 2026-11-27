package com.chatbot.financeiro.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Integer> {

    List<Lancamento> findByUserId(Integer userId);

    List<Lancamento> findByUserIdAndTipo(Integer userId, TipoLancamento tipo);

    List<Lancamento> findByUserIdAndStatus(Integer userId, StatusLancamento status);

    List<Lancamento> findByUserIdAndDataVencimentoBetween(
            Integer userId,
            LocalDate inicio,
            LocalDate fim
    );
}
