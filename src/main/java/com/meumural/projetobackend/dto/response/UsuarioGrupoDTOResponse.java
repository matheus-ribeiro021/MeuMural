package com.meumural.projetobackend.dto.response;

import java.time.LocalDateTime;

public class UsuarioGrupoDTOResponse {

    private UsuarioDTOResponse usuario;
    private GrupoDTOResponse grupo;
    private LocalDateTime dataEntrada;

    // Getters e Setters


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

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
