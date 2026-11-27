package com.chatbot.financeiro.categoria;

import com.chatbot.financeiro.user.User;
import com.chatbot.financeiro.user.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service 
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UserService userService;

    public CategoriaService(CategoriaRepository categoriaRepository, UserService userService) {
        this.categoriaRepository = categoriaRepository;
        this.userService = userService;
    }

    public CategoriaResponse criar(Integer userId, CategoriaRequest request) {
        User user = userService.buscarEntidadePorId(userId);

        if (request.nome() == null || request.nome().isBlank()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        if (request.tipo() == null) {
            throw new IllegalArgumentException("O tipo da categoria (RECEITA ou DESPESA) é obrigatório.");
        }
        if (categoriaRepository.existsByUserIdAndNomeIgnoreCase(userId, request.nome().trim())) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome para este usuário.");
        }

        Categoria categoria = new Categoria(request.nome().trim(), request.tipo(), user);
        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponse> listarPorUsuario(Integer userId, TipoCategoria tipo) {
        userService.buscarEntidadePorId(userId);
        List<Categoria> categorias = (tipo != null)
                ? categoriaRepository.findByUserIdAndTipo(userId, tipo)
                : categoriaRepository.findByUserId(userId);

        return categorias.stream()
                .map(CategoriaResponse::fromEntity)
                .toList();
    }

    public Categoria buscarEntidadePorIdEUsuario(Integer id, Integer userId) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + id));

        if (!categoria.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Acesso negado: esta categoria não pertence ao usuário informado.");
        }
        return categoria;
    }

    public CategoriaResponse buscarPorId(Integer userId, Integer id) {
        return CategoriaResponse.fromEntity(buscarEntidadePorIdEUsuario(id, userId));
    }

    public CategoriaResponse atualizar(Integer userId, Integer id, CategoriaRequest request) {
        Categoria categoriaExistente = buscarEntidadePorIdEUsuario(id, userId);

        if (request.nome() != null && !request.nome().isBlank()) {
            categoriaExistente.setNome(request.nome().trim());
        }
        if (request.tipo() != null) {
            categoriaExistente.setTipo(request.tipo());
        }

        return CategoriaResponse.fromEntity(categoriaRepository.save(categoriaExistente));
    }

    public void excluir(Integer userId, Integer id) {
        Categoria categoria = buscarEntidadePorIdEUsuario(id, userId);
        categoriaRepository.delete(categoria);
    }

}
