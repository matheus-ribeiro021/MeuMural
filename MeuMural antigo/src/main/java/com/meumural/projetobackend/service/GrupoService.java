package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.GrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.repository.GrupoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoService {

    private final ModelMapper modelMapper;
    private final GrupoRepository grupoRepository;

    public GrupoService(ModelMapper modelMapper, GrupoRepository grupoRepository) {
        this.modelMapper = modelMapper;
        this.grupoRepository = grupoRepository;
    }

    public GrupoDTOResponse salvarGrupo(GrupoDTORequest request){

        Grupo grupo = new Grupo();
        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());
        grupo.setDataCriacao(LocalDateTime.now());
        grupo.setStatus(1);

        Grupo grupoSalvo = grupoRepository.save(grupo);

        return modelMapper.map(grupoSalvo, GrupoDTOResponse.class);
    }

    public void excluirGrupo(int id){
        grupoRepository.apagarGrupo(id);
    }

    public GrupoDTOResponse retornarGrupoPorId(int id){
        Grupo grupo = grupoRepository.retornarGrupoPorId(id);
        return modelMapper.map(grupo, GrupoDTOResponse.class);
    }

    public GrupoDTOResponse atualizarGrupo(int id, GrupoDTORequest request){
        Grupo grupo = grupoRepository.retornarGrupoPorId(id);

        if (grupo == null){
            throw new IllegalArgumentException("Grupo não existe");
        }

        grupo.setNome(request.getNome());
        grupo.setDescricao(request.getDescricao());

        Grupo grupoSalvo = grupoRepository.save(grupo);

        return modelMapper.map(grupoSalvo, GrupoDTOResponse.class);
    }

    public List<GrupoDTOResponse> retornarGrupos(){
        return grupoRepository.retornarGrupos()
                .stream()
                .map(g -> modelMapper.map(g, GrupoDTOResponse.class))
                .collect(Collectors.toList());
    }
}
