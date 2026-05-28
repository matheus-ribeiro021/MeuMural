package com.meumural.projetobackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostagemDTORequest {
    private Integer usuarioId;
    private Integer grupoId;
    private String titulo;
    private String conteudo;

    public Integer getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Integer getGrupoId() {
        return grupoId;
    }
    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
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
}
