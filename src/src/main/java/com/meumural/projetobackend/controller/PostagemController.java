package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.PostagemDTORequest;
import com.meumural.projetobackend.dto.response.PostagemDTOResponse;
import com.meumural.projetobackend.service.PostagemService;
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
    public ResponseEntity<List<PostagemDTOResponse>> listarPostagens() {
        return ResponseEntity.ok(postagemService.listarTodos());
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<PostagemDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        PostagemDTOResponse dto = postagemService.buscarPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    public ResponseEntity<PostagemDTOResponse> criarPostagem(@Valid @RequestBody PostagemDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemService.salvar(dto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<PostagemDTOResponse> atualizarPostagem(
            @PathVariable("id") Integer id,
            @Valid @RequestBody PostagemDTORequest dto) {
        return ResponseEntity.ok(postagemService.atualizar(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagarPostagem(@PathVariable("id") Integer id) {
        postagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
