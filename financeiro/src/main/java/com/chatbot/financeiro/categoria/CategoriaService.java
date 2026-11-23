package com.chatbot.financeiro.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chatbot.financeiro.user.User;
import com.chatbot.financeiro.user.UserService;

@Service 
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UserService userService;
    
    public CategoriaService(CategoriaRepository categoriaRepository, UserService userService) {
        this.categoriaRepository = categoriaRepository;
        this.userService = userService;
    }

    public Categoria criar(Integer userId, Categoria categoria) {
        User user = userService.buscarPorId(userId);

        if (categoriaRepository.existsByUserIdAndNomeIgnoreCase(userId, categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome para este usuário.");
        }

        categoria.setUser(user);
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarPorUsuario(Integer userId, TipoCategoria tipo) {
        userService.buscarPorId(userId);
        if (tipo != null) {
            return categoriaRepository.findByUserIdAndTipo(userId, tipo);
        }
        return categoriaRepository.findByUserId(userId);
    }

    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));
    }

    public Categoria atualizar(Integer id, Categoria dadosAtualizados) {
        Categoria categoriaExistente = buscarPorId(id);

        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()) {
            categoriaExistente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getTipo() != null) {
            categoriaExistente.setTipo(dadosAtualizados.getTipo());
        }

        return categoriaRepository.save(categoriaExistente);
    }

    public void excluir(Integer id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }

}
