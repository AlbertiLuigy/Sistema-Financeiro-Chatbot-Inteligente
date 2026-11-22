package com.chatbot.financeiro.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { // Injeção de dependência do UserRepository
        this.userRepository = userRepository;
    }

    public User cadastrar(User user) { // Método para cadastrar um novo usuário
        if (userRepository.existsByEmail(user.getEmail())) { // Verifica se o email já está cadastrado
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        if (userRepository.existsByDocumento(user.getDocumento())) { // Verifica se o documento (CPF/CNPJ) já está cadastrado
            throw new IllegalArgumentException("Documento (CPF/CNPJ) já cadastrado.");
        }

        // TODO: criptografar senha com BCrypt quando adicionarmos Spring Security
        user.setSenhaHash(user.getSenhaHash());
        user.setAtivo(true);

        return userRepository.save(user);
    }

    public List<User> listarTodos() { // Método para listar todos os usuários
        return userRepository.findAll();
    }

    public User buscarPorId(Integer id) { // Método para buscar um usuário pelo ID
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    public void desativar(Integer id) { // Método para desativar um usuário pelo ID
        User user = buscarPorId(id);
        user.setAtivo(false);
        userRepository.save(user);
    }
}
