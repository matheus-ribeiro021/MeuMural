package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.GrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.repository.GrupoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoService {

    private final GrupoRepository grupoRepository;

    public GrupoService(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public GrupoDTOResponse salvarGrupo(GrupoDTORequest request) {
        Grupo grupo = new Grupo();
        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());
        grupo.setDataCriacao(LocalDateTime.now());
        grupo.setStatus(1);

        Grupo grupoSalvo = grupoRepository.save(grupo);
        return toResponse(grupoSalvo);
    }

    public void excluirGrupo(int id) {
        grupoRepository.deleteById(id);
    }

    public GrupoDTOResponse retornarGrupoPorId(int id) {
        Grupo grupo = grupoRepository.findById(id).orElse(null);
        if (grupo == null) return null;
        return toResponse(grupo);
    }

    public GrupoDTOResponse atualizarGrupo(int id, GrupoDTORequest request) {
        Grupo grupo = grupoRepository.findById(id).orElse(null);
        if (grupo == null) {
            throw new IllegalArgumentException("Grupo nao existe");
        }
        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());
        return toResponse(grupoRepository.save(grupo));
    }

    public List<GrupoDTOResponse> retornarGrupos() {
        return grupoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private GrupoDTOResponse toResponse(Grupo grupo) {
        GrupoDTOResponse response = new GrupoDTOResponse();
        response.setId(grupo.getId());
        response.setNome(grupo.getNome());
        response.setDescricao(grupo.getDescricao());
        response.setDataCriacao(grupo.getDataCriacao());
        response.setStatus(grupo.getStatus());
        return response;
    }
}
