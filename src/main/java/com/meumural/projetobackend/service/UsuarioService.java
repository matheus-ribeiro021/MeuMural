package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    // Criar usuário
    public UsuarioDTOResponse salvar(UsuarioDTORequest request) {
       Usuario usuario = modelMapper.map(request, Usuario.class);
       usuario.setStatus(1);
       Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
       return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
    }

    // Listar todos
    public List<UsuarioDTOResponse> listarTodos() {
       List<Usuario> usuarios = this.usuarioRepository.retornarUsuarios();
       List<UsuarioDTOResponse> responses = usuarios.stream().
               map(usuario -> modelMapper.map(usuario,UsuarioDTOResponse.class)).
               collect(Collectors.toList());
       return responses;
    }

    // Buscar por ID
    public UsuarioDTOResponse buscarPorId(int id) {
        return modelMapper.map(this.usuarioRepository.retornarUsuarioPorId(id), UsuarioDTOResponse.class);
    }

    // Atualizar
    public UsuarioDTOResponse atualizar(int id, UsuarioDTORequest request) {
        Usuario usuarioBuscado = this.usuarioRepository.retornarUsuarioPorId(id);
        if (usuarioBuscado == null){
            throw new IllegalArgumentException("Usuario com id("+id+") não existe");
        }
        modelMapper.map(request,usuarioBuscado);
        Usuario usuarioAtualizado = this.usuarioRepository.save(usuarioBuscado);
        return modelMapper.map(usuarioAtualizado,UsuarioDTOResponse.class);
    }

    // Deletar
    public void deletar(int id) {
        this.usuarioRepository.apagarUsuario(id);
    }
}
