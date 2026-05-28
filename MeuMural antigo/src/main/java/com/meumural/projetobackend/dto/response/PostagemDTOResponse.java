package com.meumural.projetobackend.dto.response;

import java.time.LocalDateTime;

public class PostagemDTOResponse {

    private int id;
    private UsuarioDTOResponse usuario;
    private GrupoDTOResponse grupo;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getConteudo() {
        return conteudo;
    }
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public UsuarioDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTOResponse usuario) {
        this.usuario = usuario;
    }

    public GrupoDTOResponse getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTOResponse grupo) {
        this.grupo = grupo;
    }
}
