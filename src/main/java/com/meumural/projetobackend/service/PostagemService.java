package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.PostagemDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.dto.response.PostagemDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.GrupoRepository;
import com.meumural.projetobackend.repository.PostagemRepository;
import com.meumural.projetobackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostagemService {

    private final PostagemRepository postagemRepository;
    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;

    public PostagemService(PostagemRepository postagemRepository,
                           UsuarioRepository usuarioRepository,
                           GrupoRepository grupoRepository) {
        this.postagemRepository = postagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }

    public PostagemDTOResponse salvar(PostagemDTORequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        Grupo grupo = grupoRepository.findById(request.getGrupoId()).orElse(null);

        Postagem postagem = new Postagem();
        postagem.setUsuario(usuario);
        postagem.setGrupo(grupo);
        postagem.setTitulo(request.getTitulo());
        postagem.setConteudo(request.getConteudo());
        postagem.setDataCriacao(LocalDateTime.now());
        postagem.setStatus(1);

        return toResponse(postagemRepository.save(postagem));
    }

    public List<PostagemDTOResponse> listarTodos() {
        return postagemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PostagemDTOResponse buscarPorId(int id) {
        Postagem postagem = postagemRepository.findById(id).orElse(null);
        if (postagem == null) return null;
        return toResponse(postagem);
    }

    public PostagemDTOResponse atualizar(int id, PostagemDTORequest request) {
        Postagem postagem = postagemRepository.findById(id).orElse(null);
        if (postagem == null) {
            throw new IllegalArgumentException("Postagem inexistente");
        }
        postagem.setTitulo(request.getTitulo());
        postagem.setConteudo(request.getConteudo());
        return toResponse(postagemRepository.save(postagem));
    }

    public void deletar(int id) {
        postagemRepository.deleteById(id);
    }

    private PostagemDTOResponse toResponse(Postagem postagem) {
        PostagemDTOResponse response = new PostagemDTOResponse();
        response.setId(postagem.getId());
        response.setTitulo(postagem.getTitulo());
        response.setConteudo(postagem.getConteudo());
        response.setDataCriacao(postagem.getDataCriacao());

        if (postagem.getUsuario() != null) {
            UsuarioDTOResponse u = new UsuarioDTOResponse();
            u.setId(postagem.getUsuario().getId());
            u.setNome(postagem.getUsuario().getNome());
            u.setEmail(postagem.getUsuario().getEmail());
            response.setUsuario(u);
        }

        if (postagem.getGrupo() != null) {
            GrupoDTOResponse g = new GrupoDTOResponse();
            g.setId(postagem.getGrupo().getId());
            g.setNome(postagem.getGrupo().getNome());
            response.setGrupo(g);
        }

        return response;
    }
}
