package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.PostagemDTORequest;
import com.meumural.projetobackend.dto.response.PostagemDTOResponse;
import com.meumural.projetobackend.service.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/postagem")
public class PostagemController {

    private final PostagemService postagemService;

    public PostagemController(PostagemService postagemService) {
        this.postagemService = postagemService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar postagens", description = "Endpoint para listar todas as postagens")
    public ResponseEntity<List<PostagemDTOResponse>> listarPostagens() {
        return ResponseEntity.ok(postagemService.listarTodos());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar postagem por ID", description = "Endpoint para listar uma postagem específica por ID")
    public ResponseEntity<PostagemDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        PostagemDTOResponse dto = postagemService.buscarPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar postagem", description = "Endpoint para criar uma nova postagem")
    public ResponseEntity<PostagemDTOResponse> criarPostagem(
            @Valid @RequestBody PostagemDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemService.salvar(dto));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar postagem", description = "Endpoint para atualizar todos os dados de uma postagem existente")
    public ResponseEntity<PostagemDTOResponse> atualizarPostagem(
            @PathVariable("id") Integer id,
            @Valid @RequestBody PostagemDTORequest dto) {
        return ResponseEntity.ok(postagemService.atualizar(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    @Operation(summary = "Apagar postagem", description = "Endpoint para apagar uma postagem")
    public ResponseEntity<Void> apagarPostagem(@PathVariable("id") Integer id) {
        postagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/listarPorUsuario/{idUsuario}")
    @Operation(summary = "Listar postagens por usuário", description = "Endpoint para listar todas as postagens de um usuário específico")
    public ResponseEntity<List<PostagemDTOResponse>> listarPorUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        List<PostagemDTOResponse> postagens = postagemService.listarPorUsuario(idUsuario);
        if (postagens.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(postagens);
    }

    @GetMapping("/listarPorGrupo/{idGrupo}")
    @Operation(summary = "Listar postagens por grupo", description = "Endpoint para listar todas as postagens de um grupo específico")
    public ResponseEntity<List<PostagemDTOResponse>> listarPorGrupo(@PathVariable("idGrupo") Integer idGrupo) {
        List<PostagemDTOResponse> postagens = postagemService.listarPorGrupo(idGrupo);
        if (postagens.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(postagens);
    }
}
