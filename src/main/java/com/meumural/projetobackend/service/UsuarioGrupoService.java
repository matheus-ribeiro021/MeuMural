package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.UsuarioGrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioGrupoDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.entity.UsuarioGrupo;
import com.meumural.projetobackend.repository.GrupoRepository;
import com.meumural.projetobackend.repository.UsuarioGrupoRepository;
import com.meumural.projetobackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioGrupoService {

    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;

    public UsuarioGrupoService(UsuarioGrupoRepository usuarioGrupoRepository,
                               UsuarioRepository usuarioRepository,
                               GrupoRepository grupoRepository) {
        this.usuarioGrupoRepository = usuarioGrupoRepository;
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }

    public UsuarioGrupoDTOResponse criarUsuarioGrupo(UsuarioGrupoDTORequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        Grupo grupo = grupoRepository.findById(request.getGrupoId()).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao existe");
        }
        if (grupo == null) {
            throw new IllegalArgumentException("Grupo nao existe");
        }

        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setGrupo(grupo);
        usuarioGrupo.setDataEntrada(LocalDateTime.now());
        usuarioGrupo.setStatus(1);

        UsuarioGrupo salvo = usuarioGrupoRepository.save(usuarioGrupo);

        UsuarioGrupoDTOResponse response = new UsuarioGrupoDTOResponse();
        response.setDataEntrada(salvo.getDataEntrada());

        UsuarioDTOResponse u = new UsuarioDTOResponse();
        u.setId(usuario.getId());
        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        response.setUsuario(u);

        GrupoDTOResponse g = new GrupoDTOResponse();
        g.setId(grupo.getId());
        g.setNome(grupo.getNome());
        response.setGrupo(g);

        return response;
    }

    public List<GrupoDTOResponse> retornarGruposPorUsuarioId(int id) {
        return usuarioGrupoRepository.retornarGruposPorUsuarioId(id)
                .stream()
                .map(grupo -> {
                    GrupoDTOResponse g = new GrupoDTOResponse();
                    g.setId(grupo.getId());
                    g.setNome(grupo.getNome());
                    g.setDescricao(grupo.getDescricao());
                    return g;
                })
                .collect(Collectors.toList());
    }

    public List<UsuarioDTOResponse> retornarUsuariosPorGrupoId(int id) {
        return usuarioGrupoRepository.retornaUsuariosPorGrupoId(id)
                .stream()
                .map(usuario -> {
                    UsuarioDTOResponse u = new UsuarioDTOResponse();
                    u.setId(usuario.getId());
                    u.setNome(usuario.getNome());
                    u.setEmail(usuario.getEmail());
                    return u;
                })
                .collect(Collectors.toList());
    }
}
