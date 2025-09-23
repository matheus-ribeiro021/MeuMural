package com.meumural.projetobackend.entity;


import jakarta.persistence.*;

import java.io.File;
import java.sql.Blob;
import java.util.Set;
@Entity
@Table(name = "arquivo_postagem")
public class ArquivoPostagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arquivo_postagem_id")
    private int id;

    @Column(name = "arquivo_postagem_nome")
    private String nome;

    @Column(name = "arquivo_postagem_tipo")
    private String tipo;

    @Column(name = "arquivo_postagem_file")
    private File file;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;

    @Column(name = "arquivo_postagem_status")
    private int status;

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}