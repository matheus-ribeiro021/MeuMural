package com.meumural.projetobackend.dto.response;

public class ArquivoPostagemDTOResponse {

    private int id;
    private PostagemDTOResponse postagem;
    private String nomeArquivo;
    private String caminho;
    private String tipo;

    // getters e setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public PostagemDTOResponse getPostagem() {
        return postagem;
    }

    public void setPostagem(PostagemDTOResponse postagem) {
        this.postagem = postagem;
    }
}
