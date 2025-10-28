package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.UsuarioGrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioDTOResponse;
import com.meumural.projetobackend.dto.response.UsuarioGrupoDTOResponse;
import com.meumural.projetobackend.service.UsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-grupo")
@CrossOrigin(origins = "*")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;


    @PostMapping
    public ResponseEntity<UsuarioGrupoDTOResponse> criarUsuarioGrupo(@RequestBody UsuarioGrupoDTORequest request) {
        UsuarioGrupoDTOResponse response = usuarioGrupoService.criarUsuarioGrupo(request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<GrupoDTOResponse>> retornarGruposPorUsuario(@PathVariable int usuarioId) {
        List<GrupoDTOResponse> grupos = usuarioGrupoService.retornarGruposPorUsuarioId(usuarioId);
        return ResponseEntity.ok(grupos);
    }


    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<UsuarioDTOResponse>> retornarUsuariosPorGrupo(@PathVariable int grupoId) {
        List<UsuarioDTOResponse> usuarios = usuarioGrupoService.retornarUsuariosPorGrupoId(grupoId);
        return ResponseEntity.ok(usuarios);
    }
}
