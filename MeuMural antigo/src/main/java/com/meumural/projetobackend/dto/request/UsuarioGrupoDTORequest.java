package com.meumural.projetobackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class UsuarioGrupoDTORequest {
    private Integer usuarioId;
    private Integer grupoId;

    // Getters e Setters
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
}
