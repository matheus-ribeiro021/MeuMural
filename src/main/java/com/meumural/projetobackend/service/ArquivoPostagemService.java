package com.meumural.projetobackend.service;

import com.meumural.projetobackend.entity.ArquivoPostagem;
import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.repository.ArquivoPostagemRepository;
import com.meumural.projetobackend.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArquivoPostagemService {

    @Autowired
    private ArquivoPostagemRepository arquivoPostagemRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    public List<ArquivoPostagem> listarTodos() {
        return arquivoPostagemRepository.findAll();
    }

    public Optional<ArquivoPostagem> buscarPorId(int id) {
        return arquivoPostagemRepository.findById(id);
    }

    public List<ArquivoPostagem> buscarPorPostagem(int postagemId) {
        return arquivoPostagemRepository.findByPostagemId(postagemId);
    }

    public ArquivoPostagem salvar(ArquivoPostagem arquivo, int postagemId) {
        Optional<Postagem> postagem = postagemRepository.findById(postagemId);
        if (postagem.isPresent()) {
            arquivo.setPostagem(postagem.get());
            arquivo.setStatus(1); // ativo por padrão
            return arquivoPostagemRepository.save(arquivo);
        } else {
            throw new RuntimeException("Postagem não encontrada com ID: " + postagemId);
        }
    }

    public ArquivoPostagem atualizar(int id, ArquivoPostagem novoArquivo) {
        Optional<ArquivoPostagem> existente = arquivoPostagemRepository.findById(id);
        if (existente.isPresent()) {
            ArquivoPostagem arquivo = existente.get();
            arquivo.setNome(novoArquivo.getNome());
            arquivo.setTipo(novoArquivo.getTipo());
            arquivo.setFile(novoArquivo.getFile());
            arquivo.setStatus(novoArquivo.getStatus());
            return arquivoPostagemRepository.save(arquivo);
        } else {
            throw new RuntimeException("Arquivo não encontrado com ID: " + id);
        }
    }

    public void excluir(int id) {
        Optional<ArquivoPostagem> arquivo = arquivoPostagemRepository.findById(id);
        if (arquivo.isPresent()) {
            ArquivoPostagem a = arquivo.get();
            a.setStatus(0);
            arquivoPostagemRepository.save(a);
        } else {
            throw new RuntimeException("Arquivo não encontrado para exclusão com ID: " + id);
        }
    }
}
