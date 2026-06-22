package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.LoginDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.response.LoginDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setStatus(1);

        Usuario salvo = usuarioRepository.save(usuario);
        return toResponse(salvo);
    }

    public List<UsuarioDTOResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UsuarioDTOResponse buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) return null;
        return toResponse(usuario);
    }

    public UsuarioDTOResponse atualizar(Integer id, UsuarioDTORequest request) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario com id(" + id + ") nao existe");
        }
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        return toResponse(usuarioRepository.save(usuario));
    }

    public LoginDTOResponse login(LoginDTORequest request) {
        Usuario usuario = usuarioRepository
                .findByEmailAndSenha(request.getEmail(), request.getSenha())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));
        String token = UUID.randomUUID().toString();
        return new LoginDTOResponse(token, toResponse(usuario));
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTOResponse toResponse(Usuario usuario) {
        UsuarioDTOResponse response = new UsuarioDTOResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setEmail(usuario.getEmail());
        response.setDataCriacao(usuario.getDataCriacao());
        response.setStatus((int) usuario.getStatus());
        return response;
    }
}
