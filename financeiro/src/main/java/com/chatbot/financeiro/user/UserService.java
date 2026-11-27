package com.chatbot.financeiro.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service 
public class UserService {
    
   private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse cadastrar(UserCreateRequest request) {
        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (request.documento() == null || request.documento().isBlank()) {
            throw new IllegalArgumentException("O documento (CPF/CNPJ) é obrigatório.");
        }
        if (request.senha() == null || request.senha().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        if (userRepository.existsByDocumento(request.documento())) {
            throw new IllegalArgumentException("Documento (CPF/CNPJ) já cadastrado.");
        }

        User user = new User();
        user.setTipoPessoa(request.tipoPessoa());
        user.setNomeOuRazao(request.nomeOuRazao());
        user.setDocumento(request.documento());
        user.setDataNascimento(request.dataNascimento());
        user.setEmail(request.email());
        // TODO: codificar palavra-passe com BCrypt quando adicionarmos Spring Security
        user.setSenhaHash(request.senha());
        user.setRendaOuFaturamento(request.rendaOuFaturamento());
        user.setCep(request.cep());
        user.setAtivo(true);

        User salvo = userRepository.save(user);
        return UserResponse.fromEntity(salvo);
    }

    public List<UserResponse> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    public User buscarEntidadePorId(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado com ID: " + id));
    }

    public UserResponse buscarPorId(Integer id) {
        return UserResponse.fromEntity(buscarEntidadePorId(id));
    }

    public void desativar(Integer id) {
        User user = buscarEntidadePorId(id);
        user.setAtivo(false);
        userRepository.save(user);
    }
}
