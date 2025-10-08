package com.meumural.projetobackend.controller;


import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.request.UsuarioEmailDTORequest;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOUpdateResponse;
import com.meumural.projetobackend.dto.response.UsuarioEmailDTOResponse;
import com.meumural.projetobackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Endpoint para listar todos os usuários")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar usuário por ID", description = "Endpoint para listar usuário por ID")
    public ResponseEntity<UsuarioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        UsuarioDTOResponse dto = usuarioService.buscarPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar usuário", description = "Endpoint para criar um novo usuário")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.criarUsuario(dto));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar usuário", description = "Endpoint para atualizar todos os dados do usuário")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @PatchMapping("/atualizarStatus/{id}")
    @Operation(summary = "Atualizar status do usuário", description = "Endpoint para atualizar apenas o status")
    public ResponseEntity<UsuarioDTOUpdateResponse> atualizarStatus(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.ok(usuarioService.atualizarStatusUsuario(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Apagar usuário", description = "Endpoint para apagar usuário")
    public ResponseEntity<Void> apagar(@PathVariable("id") Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/email")
    public ResponseEntity<UsuarioEmailDTOResponse> email(@RequestBody UsuarioEmailDTORequest usuarioEmailDTORequest){
        return ResponseEntity.ok(usuarioService.email(usuarioEmailDTORequest));

    }

}