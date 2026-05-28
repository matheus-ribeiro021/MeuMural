package com.meumural.projetobackend.service;

import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioEmailDTORequest;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOUpdateResponse;
import com.meumural.projetobackend.dto.response.UsuarioEmailDTOResponse;
import com.meumural.projetobackend.dto.roles.LoginUserDTO;
import com.meumural.projetobackend.dto.roles.RecoveryJwtTokenDto;
import com.meumural.projetobackend.entity.Role;
import com.meumural.projetobackend.entity.RoleName;
import com.meumural.projetobackend.entity.Usuario;
import com.meumural.projetobackend.repository.RoleRepository;
import com.meumural.projetobackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {

        Role roleUsuario = roleRepository.findByName(RoleName.ROLE_USUARIO);
        List<Role> roles = new ArrayList<>();
        roles.add(roleUsuario);

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setStatus(1);
        usuario.setRoles(roles);

        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }


    public UsuarioDTOResponse salvarUsuario(UsuarioDTORequest usuarioRequest) {
        Role roleUsuario = roleRepository.findByName(RoleName.ROLE_USUARIO);
        List<Role> roles = new ArrayList<>();
        roles.add(roleUsuario);

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioRequest.getNome());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        usuario.setStatus(1);
        usuario.setRoles(roles);

        Usuario usuarioSave = this.usuarioRepository.save(usuario);

        return modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
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

    public RecoveryJwtTokenDto authenticateUser(LoginUserDTO loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }
}
