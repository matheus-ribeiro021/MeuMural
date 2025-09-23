package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.PostagemDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.response.PostagemDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.GrupoRepository;
import com.meumural.projetobackend.repository.PostagemRepository;
import com.meumural.projetobackend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostagemService {
    private final PostagemRepository postagemRepository;
    private final ModelMapper modelMapper;
    private UsuarioRepository usuarioRepository;
    private GrupoRepository grupoRepository;

    public PostagemService(PostagemRepository postagemRepository, ModelMapper modelMapper) {
        this.postagemRepository = postagemRepository;
        this.modelMapper = modelMapper;
    }


    public PostagemDTOResponse salvar(PostagemDTORequest request) {
        Postagem postagem = modelMapper.map(request, Postagem.class);
        postagem.setUsuario(usuarioRepository.retornarUsuarioPorId(request.getUsuarioId()));
        postagem.setGrupo(grupoRepository.retornarGrupoPorId(request.getGrupoId()));
        postagem.setStatus(1);
        Postagem postagemSalvo = this.postagemRepository.save(postagem);
        return modelMapper.map(postagemSalvo, PostagemDTOResponse.class);
    }

    // Listar todos
    public List<PostagemDTOResponse> listarTodos() {
        List<Postagem> postagens = this.postagemRepository.listarPostagem();
        List<PostagemDTOResponse> responses = postagens.stream().
                map(postagem -> modelMapper.map(postagem,PostagemDTOResponse.class)).
                collect(Collectors.toList());
        return responses;
    }
    // Buscar por ID
    public PostagemDTOResponse buscarPorId(int id) {
        return modelMapper.map(this.postagemRepository.postagemPorId(id), PostagemDTOResponse.class);
    }

    // Atualizar
    public PostagemDTOResponse atualizar(int id, PostagemDTORequest request) {
        Postagem postagemBuscada = postagemRepository.postagemPorId(id);
        if (postagemBuscada == null){
            throw new IllegalArgumentException("Postagem inexistente");
        }
        modelMapper.map(request, postagemBuscada);
        Postagem postagemAtualizada = this.postagemRepository.save(postagemBuscada);
        return modelMapper.map(postagemAtualizada, PostagemDTOResponse.class);
    }

    // Deletar
    public void deletar(int id) {
        this.postagemRepository.apagarPostagem(id);
    }
}
