package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.UsuarioDTORequest;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.service.UsuarioService;
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
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<UsuarioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        UsuarioDTOResponse dto = usuarioService.buscarPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.salvarUsuario(dto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UsuarioDTORequest dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagar(@PathVariable("id") Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}