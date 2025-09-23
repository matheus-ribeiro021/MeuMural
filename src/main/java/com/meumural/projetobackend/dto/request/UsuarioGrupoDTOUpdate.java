package com.meumural.projetobackend.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UsuarioGrupoDTOUpdate {

    @NotNull(message = "usuarioId é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "grupoId é obrigatório")
    private Integer grupoId;

    private LocalDateTime dataEntrada;

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

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
