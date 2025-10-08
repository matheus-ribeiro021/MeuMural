package com.meumural.projetobackend.service;

import com.meumural.projetobackend.config.SecurityConfiguration;
import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioEmailDTORequest;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOUpdateResponse;
import com.meumural.projetobackend.dto.response.UsuarioEmailDTOResponse;
import com.meumural.projetobackend.entity.Role;
import com.meumural.projetobackend.entity.RoleName;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        List<Role> roles = new ArrayList<Role>();
        Role role = null;
        for (int i = 0; i < usuarioDTORequest.getRolesList().size(); i++) {
            role = new Role();
            role.setName(RoleName.valueOf(usuarioDTORequest.getRolesList().get(i)));
            roles.add(role);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuarioDTORequest.getSenha()));
        usuario.setRoles(List.of(role));
        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }

//    // Criar usuário
//    public UsuarioDTOResponse salvar(UsuarioDTORequest request) {
//       Usuario usuario = modelMapper.map(request, Usuario.class);
//       usuario.setStatus(1);
//       Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
//       return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
//    }

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

    //Atualizar Status
    @Transactional
    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer id, UsuarioDTORequest dto) {
        Usuario usuario = usuarioRepository.retornarUsuarioPorId(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado");
        }

        usuario.setStatus(dto.getStatus());
        Usuario salvo = usuarioRepository.save(usuario);
        return modelMapper.map(salvo, UsuarioDTOUpdateResponse.class);
    }

    // Deletar
    public void deletar(int id) {
        this.usuarioRepository.apagarUsuario(id);
    }

    public UsuarioEmailDTOResponse email(UsuarioEmailDTORequest usuarioEmailDTORequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioEmailDTORequest.getEmail(), usuarioEmailDTORequest.getSenha());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        UsuarioEmailDTOResponse usuarioEmailDTOResponse = new UsuarioEmailDTOResponse();
        usuarioEmailDTOResponse.setId(userDetails.getIdUsuario());
        usuarioEmailDTOResponse.setNome(userDetails.getNomeUsuario());
        usuarioEmailDTOResponse.setToken(jwtTokenService.generateToken(userDetails));
        return usuarioEmailDTOResponse;
    }
}
