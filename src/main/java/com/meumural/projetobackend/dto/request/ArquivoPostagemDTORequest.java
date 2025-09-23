package com.meumural.projetobackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArquivoPostagemDTORequest {

    private Integer idPostagem;
    private String nomeArquivo;
    private String caminho;
    private String tipo;

    // getters e setters
    public Integer getIdPostagem() {
        return idPostagem;
    }
    public void setIdPostagem(Integer idPostagem) {
        this.idPostagem = idPostagem;
    }
    public String getNomeArquivo() {
        return nomeArquivo;
    }
    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
    public String getCaminho() {
        return caminho;
    }
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
