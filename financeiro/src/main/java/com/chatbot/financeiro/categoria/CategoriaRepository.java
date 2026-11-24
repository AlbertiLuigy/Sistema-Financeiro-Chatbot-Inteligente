package com.chatbot.financeiro.categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Aqui você pode adicionar métodos de consulta personalizados, se necessário
    
    List<Categoria> findByUserId(Integer userId); // Método para buscar categorias por ID do usuário
    List<Categoria> findByUserIdAndTipo(Integer userId, TipoCategoria tipo); // Método para buscar categorias por ID do usuário e tipo de categoria
    boolean existsByUserIdAndNomeIgnoreCase(Integer userId, String nome); // Método para verificar se uma categoria com determinado nome já existe para um usuário específico, ignorando diferenças de maiúsculas e minúsculas
}
