package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.UsuarioGrupoDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioGrupoDTOUpdate;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioGrupoDTOResponse;
import com.meumural.projetobackend.entity.*;
import com.meumural.projetobackend.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioGrupoService {
    private ModelMapper modelMapper;
    private UsuarioGrupoRepository usuarioGrupoRepository;
    private UsuarioRepository usuarioRepository;
    private GrupoRepository grupoRepository;

    public UsuarioGrupoService(ModelMapper modelMapper, UsuarioGrupoRepository usuarioGrupoRepository, UsuarioRepository usuarioRepository, GrupoRepository grupoRepository) {
        this.modelMapper = modelMapper;
        this.usuarioGrupoRepository = usuarioGrupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }

    public UsuarioGrupoDTOResponse criarUsuarioGrupo(UsuarioGrupoDTORequest request){
        Usuario usuario = usuarioRepository.retornarUsuarioPorId(request.getUsuarioId());
        Grupo grupo = grupoRepository.retornarGrupoPorId(request.getGrupoId());
        if (usuario == null){
            throw new IllegalArgumentException("Usuario não existe");
        }
        if (grupo == null){
            throw new IllegalArgumentException("Grupo não existe");
        }
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setGrupo(grupo);
        usuarioGrupo.setDataEntrada(LocalDateTime.now());
        usuarioGrupo.setStatus(1);

        UsuarioGrupo usuarioGrupoSalvo = usuarioGrupoRepository.save(usuarioGrupo);
        return modelMapper.map(usuarioGrupoSalvo, UsuarioGrupoDTOResponse.class);
    }

    public List<GrupoDTOResponse> retornarGruposPorUsuarioId(int id){
        Usuario usuario = usuarioRepository.retornarUsuarioPorId(id);
        if (usuario == null){
            throw new IllegalArgumentException("Usuario não existe");
        }
        List<Grupo> gruposDoUsuario = usuarioGrupoRepository.retornarGruposPorUsuarioId(id);
        List<GrupoDTOResponse> responses = gruposDoUsuario.stream()
                .map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    public List<UsuarioDTOResponse> retornarUsuariosPorGrupoId(int id){
        Grupo grupo = grupoRepository.retornarGrupoPorId(id);
        if (grupo == null){
            throw new IllegalArgumentException("Grupo não existe");
        }
        List<Usuario> usuarios = usuarioGrupoRepository.retornaUsuariosPorGrupoId(id);
        List<UsuarioDTOResponse> responses = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

}
