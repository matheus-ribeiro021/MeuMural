package com.meumural.projetobackend.dto.response;

public class LoginDTOResponse {

    private String token;
    private UsuarioDTOResponse usuario;

    public LoginDTOResponse(String token, UsuarioDTOResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public UsuarioDTOResponse getUsuario() {
        return usuario;
    }
}
