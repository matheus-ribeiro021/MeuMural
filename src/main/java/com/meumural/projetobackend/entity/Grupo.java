package com.meumural.projetobackend.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grupo_id")
    private Integer id;

    @Column(name = "grupo_nome", length = 100)
    private String nome;

    @Column(name = "grupo_descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "grupo_data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "grupo_status")
    private int status;
    @OneToMany(mappedBy = "grupo")
    private List<UsuarioGrupo> usuarioGrupo;

    @OneToMany(mappedBy = "grupo")
    private List<Postagem> postagens;


    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<UsuarioGrupo> getUsuarioGrupo() {
        return usuarioGrupo;
    }

    public void setUsuarioGrupo(List<UsuarioGrupo> usuarioGrupo) {
        this.usuarioGrupo = usuarioGrupo;
    }

    public List<Postagem> getPostagens() {
        return postagens;
    }

    public void setPostagens(List<Postagem> postagens) {
        this.postagens = postagens;
    }
}