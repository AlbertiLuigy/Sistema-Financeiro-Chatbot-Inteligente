package com.chatbot.financeiro.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); // Método para buscar um usuário pelo email
    boolean existsByEmail(String email); // Método para verificar se um usuário com determinado email já existe
    boolean existsByDocumento(String documento); // Método para verificar se um usuário com determinado documento já existe
}
