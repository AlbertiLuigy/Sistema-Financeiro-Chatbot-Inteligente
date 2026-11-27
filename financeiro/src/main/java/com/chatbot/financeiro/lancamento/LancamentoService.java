package com.chatbot.financeiro.lancamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chatbot.financeiro.categoria.Categoria;
import com.chatbot.financeiro.categoria.CategoriaService;
import com.chatbot.financeiro.user.User;
import com.chatbot.financeiro.user.UserService;

@Service 
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final UserService userService;
    private final CategoriaService categoriaService;

    public LancamentoService(
            LancamentoRepository lancamentoRepository,
            UserService userService,
            CategoriaService categoriaService
    ) {
        this.lancamentoRepository = lancamentoRepository;
        this.userService = userService;
        this.categoriaService = categoriaService;
    }

    public LancamentoResponse criar(Integer userId, LancamentoRequest request) {
        User user = userService.buscarEntidadePorId(userId);
        Categoria categoria = categoriaService.buscarEntidadePorIdEUsuario(request.categoriaId(), userId);

        if (request.descricao() == null || request.descricao().isBlank()) {
            throw new IllegalArgumentException("A descrição do lançamento é obrigatória.");
        }
        if (request.valor() == null || request.valor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do lançamento deve ser maior que zero.");
        }
        if (request.dataVencimento() == null) {
            throw new IllegalArgumentException("A data de vencimento é obrigatória.");
        }

        Lancamento lancamento = new Lancamento();
        lancamento.setUser(user);
        lancamento.setCategoria(categoria);
        lancamento.setDescricao(request.descricao().trim());
        lancamento.setValor(request.valor());
        lancamento.setTipo(request.tipo() != null
                ? request.tipo()
                : TipoLancamento.valueOf(categoria.getTipo().name()));
        lancamento.setStatus(request.status() != null ? request.status() : StatusLancamento.PENDENTE);
        lancamento.setDataVencimento(request.dataVencimento());
        lancamento.setRecorrente(request.recorrente() != null ? request.recorrente() : false);

        if (lancamento.getStatus() == StatusLancamento.PAGO && request.dataPagamento() == null) {
            lancamento.setDataPagamento(LocalDate.now());
        } else {
            lancamento.setDataPagamento(request.dataPagamento());
        }

        return LancamentoResponse.fromEntity(lancamentoRepository.save(lancamento));
    }

    public List<LancamentoResponse> listarPorUsuario(
            Integer userId,
            TipoLancamento tipo,
            StatusLancamento status
    ) {
        userService.buscarEntidadePorId(userId);

        List<Lancamento> lista;
        if (tipo != null) {
            lista = lancamentoRepository.findByUserIdAndTipo(userId, tipo);
        } else if (status != null) {
            lista = lancamentoRepository.findByUserIdAndStatus(userId, status);
        } else {
            lista = lancamentoRepository.findByUserId(userId);
        }

        return lista.stream()
                .map(LancamentoResponse::fromEntity)
                .toList();
    }

    public List<LancamentoResponse> listarPorPeriodo(Integer userId, LocalDate inicio, LocalDate fim) {
        userService.buscarEntidadePorId(userId);
        return lancamentoRepository.findByUserIdAndDataVencimentoBetween(userId, inicio, fim)
                .stream()
                .map(LancamentoResponse::fromEntity)
                .toList();
    }

    public Lancamento buscarEntidadePorIdEUsuario(Integer id, Integer userId) {
        Lancamento lancamento = lancamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lançamento não encontrado com ID: " + id));

        if (!lancamento.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Acesso negado: este lançamento não pertence ao usuário informado.");
        }
        return lancamento;
    }

    public LancamentoResponse buscarPorId(Integer userId, Integer id) {
        return LancamentoResponse.fromEntity(buscarEntidadePorIdEUsuario(id, userId));
    }

    public LancamentoResponse atualizar(Integer userId, Integer id, LancamentoRequest request) {
        Lancamento existente = buscarEntidadePorIdEUsuario(id, userId);

        if (request.categoriaId() != null) {
            Categoria novaCategoria = categoriaService.buscarEntidadePorIdEUsuario(request.categoriaId(), userId);
            existente.setCategoria(novaCategoria);
        }
        if (request.descricao() != null && !request.descricao().isBlank()) {
            existente.setDescricao(request.descricao().trim());
        }
        if (request.valor() != null && request.valor().compareTo(BigDecimal.ZERO) > 0) {
            existente.setValor(request.valor());
        }
        if (request.tipo() != null) {
            existente.setTipo(request.tipo());
        }
        if (request.status() != null) {
            existente.setStatus(request.status());
            if (request.status() == StatusLancamento.PAGO && existente.getDataPagamento() == null) {
                existente.setDataPagamento(
                        request.dataPagamento() != null ? request.dataPagamento() : LocalDate.now()
                );
            }
        }
        if (request.dataVencimento() != null) {
            existente.setDataVencimento(request.dataVencimento());
        }
        if (request.dataPagamento() != null) {
            existente.setDataPagamento(request.dataPagamento());
        }
        if (request.recorrente() != null) {
            existente.setRecorrente(request.recorrente());
        }

        return LancamentoResponse.fromEntity(lancamentoRepository.save(existente));
    }

    public void excluir(Integer userId, Integer id) {
        Lancamento existente = buscarEntidadePorIdEUsuario(id, userId);
        lancamentoRepository.delete(existente);
    }

    public Map<String, BigDecimal> calcularResumoPeriodo(Integer userId, LocalDate inicio, LocalDate fim) {
        userService.buscarEntidadePorId(userId);
        List<Lancamento> lancamentos = lancamentoRepository.findByUserIdAndDataVencimentoBetween(userId, inicio, fim);

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;
        BigDecimal pendenteDespesas = BigDecimal.ZERO;

        for (Lancamento item : lancamentos) {
            if (item.getStatus() == StatusLancamento.CANCELADO) {
                continue;
            }
            if (item.getTipo() == TipoLancamento.RECEITA) {
                totalReceitas = totalReceitas.add(item.getValor());
            } else if (item.getTipo() == TipoLancamento.DESPESA) {
                totalDespesas = totalDespesas.add(item.getValor());
                if (item.getStatus() == StatusLancamento.PENDENTE) {
                    pendenteDespesas = pendenteDespesas.add(item.getValor());
                }
            }
        }

        Map<String, BigDecimal> resumo = new HashMap<>();
        resumo.put("totalReceitas", totalReceitas);
        resumo.put("totalDespesas", totalDespesas);
        resumo.put("pendenteDespesas", pendenteDespesas);
        resumo.put("saldoPrevisto", totalReceitas.subtract(totalDespesas));
        return resumo;
    }
    
}
