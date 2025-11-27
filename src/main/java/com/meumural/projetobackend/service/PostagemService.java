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
    // Esses campos precisam ser injetados. Se não estiver usando injeção
    // via campo (@Autowired), eles devem ser incluídos no construtor.
    private UsuarioRepository usuarioRepository;
    private GrupoRepository grupoRepository;

    // Construtor completo com todas as dependências
    public PostagemService(PostagemRepository postagemRepository, ModelMapper modelMapper,
                           UsuarioRepository usuarioRepository, GrupoRepository grupoRepository) {
        this.postagemRepository = postagemRepository;
        this.modelMapper = modelMapper;
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }


    public PostagemDTOResponse salvar(PostagemDTORequest request) {
        Postagem postagem = modelMapper.map(request, Postagem.class);
        // ATENÇÃO: Os métodos 'retornarUsuarioPorId' e 'retornarGrupoPorId'
        // não são métodos padrão de Repository. Certifique-se de que eles
        // estão definidos no seu UsuarioRepository e GrupoRepository.
        postagem.setUsuario(usuarioRepository.retornarUsuarioPorId(request.getUsuarioId()));
        postagem.setGrupo(grupoRepository.retornarGrupoPorId(request.getGrupoId()));
        postagem.setStatus(1);
        Postagem postagemSalvo = this.postagemRepository.save(postagem);
        return modelMapper.map(postagemSalvo, PostagemDTOResponse.class);
    }

    // Listar todos
    public List<PostagemDTOResponse> listarTodos() {
        // ATENÇÃO: 'listarPostagem' não é um método padrão.
        List<Postagem> postagens = this.postagemRepository.listarPostagem();
        List<PostagemDTOResponse> responses = postagens.stream().
                map(postagem -> modelMapper.map(postagem,PostagemDTOResponse.class)).
                collect(Collectors.toList());
        return responses;
    }

    // Buscar por ID
    public PostagemDTOResponse buscarPorId(int id) {
        // ATENÇÃO: 'postagemPorId' não é um método padrão.
        return modelMapper.map(this.postagemRepository.postagemPorId(id), PostagemDTOResponse.class);
    }

    // Atualizar
    public PostagemDTOResponse atualizar(int id, PostagemDTORequest request) {
        // ATENÇÃO: 'postagemPorId' não é um método padrão.
        Postagem postagemBuscada = postagemRepository.postagemPorId(id);
        if (postagemBuscada == null){
            throw new IllegalArgumentException("Postagem inexistente");
        }
        modelMapper.map(request, postagemBuscada);
        Postagem postagemAtualizada = this.postagemRepository.save(postagemBuscada);
        return modelMapper.map(postagemAtualizada, PostagemDTOResponse.class);
    } // <--- CHAVE DE FECHAMENTO CORRIGIDA AQUI!

    // Listar postagens por usuário
    public List<PostagemDTOResponse> listarPorUsuario(Integer idUsuario) {
        // ATENÇÃO: 'listarPorUsuario' não é um método padrão.
        List<Postagem> postagens = postagemRepository.listarPorUsuario(idUsuario);
        return postagens.stream()
                .map(postagem -> modelMapper.map(postagem, PostagemDTOResponse.class))
                .collect(Collectors.toList());
    }

    // Listar postagens por grupo
    public List<PostagemDTOResponse> listarPorGrupo(Integer idGrupo) {
        // ATENÇÃO: 'listarPorGrupo' não é um método padrão.
        List<Postagem> postagens = postagemRepository.listarPorGrupo(idGrupo);
        return postagens.stream()
                .map(postagem -> modelMapper.map(postagem, PostagemDTOResponse.class))
                .collect(Collectors.toList());
    }

    // Deletar
    public void deletar(int id) {
        // ATENÇÃO: 'apagarPostagem' não é um método padrão.
        this.postagemRepository.apagarPostagem(id);
    }

}
