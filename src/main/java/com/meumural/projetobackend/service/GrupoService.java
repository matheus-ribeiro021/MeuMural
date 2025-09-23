package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.GrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.entity.Grupo;
import com.meumural.projetobackend.repository.GrupoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupoService {
    private final ModelMapper modelMapper;
    private GrupoRepository grupoRepository;

    public GrupoService(ModelMapper modelMapper, GrupoRepository grupoRepository) {
        this.modelMapper = modelMapper;
        this.grupoRepository = grupoRepository;
    }

    public GrupoDTOResponse salvarGrupo(GrupoDTORequest request){
        Grupo grupo = modelMapper.map(request,Grupo.class);
        grupo.setDataCriacao(LocalDateTime.now());
        Grupo grupoSalvo = grupoRepository.save(grupo);
        return modelMapper.map(grupoSalvo, GrupoDTOResponse.class);
    }

   public void excluirGrupo(int id){
    this.grupoRepository.apagarGrupo(id);
   }

    public GrupoDTOResponse retornarGrupoPorId(int id){
        return modelMapper.map(grupoRepository.retornarGrupoPorId(id), GrupoDTOResponse.class);
    }

    public GrupoDTOResponse atualizarGrupo(int id, GrupoDTORequest request){
        Grupo grupo = grupoRepository.retornarGrupoPorId(id);
        if (grupo == null){
            throw new IllegalArgumentException("Grupo não existe");
        }
        modelMapper.map(request,grupo);
        Grupo grupoSalvo = grupoRepository.save(grupo);
        return modelMapper.map(grupoSalvo, GrupoDTOResponse.class);
    }

    public List<GrupoDTOResponse> retornarGrupos(){
        List<Grupo> grupos = grupoRepository.retornarGrupos();
        List<GrupoDTOResponse> responses = grupos.stream().
                map(grupo -> modelMapper.map(grupo, GrupoDTOResponse.class)).
                collect(Collectors.toList());
        return responses;
    }

}
