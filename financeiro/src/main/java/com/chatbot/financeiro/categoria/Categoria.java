package com.chatbot.financeiro.categoria;

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
import jakarta.persistence.Table;

@Entity 
@Table (name = "Categorias")
public class Categoria {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idCategoria")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) // Define a relação ManyToOne com a entidade User, indicando que várias categorias podem estar associadas a um único usuário.
    @JoinColumn(name = "Users_Users", nullable = false) // Define a coluna de junção na tabela "Categorias" que referencia a tabela "Users". O nome da coluna é "Users_Users" e não pode ser nulo.
    private User user;


    @Column (name = "nome", nullable = false, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column (name = "tipo", nullable = false, length = 20)
    private TipoCategoria tipo;

    public Categoria() {
    }

    public Categoria(String nome, TipoCategoria tipo, User user) {
        this.nome = nome;
        this.tipo = tipo;
        this.user = user;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }

    public void setTipo(TipoCategoria tipo) {
        this.tipo = tipo;
    }

    
}
