package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.entity.ArquivoPostagem;
import com.meumural.projetobackend.service.ArquivoPostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivos")
@CrossOrigin(origins = "*")
public class ArquivoPostagemController {

    @Autowired
    private ArquivoPostagemService arquivoPostagemService;

    @GetMapping
    public ResponseEntity<List<ArquivoPostagem>> listarTodos() {
        return ResponseEntity.ok(arquivoPostagemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArquivoPostagem> buscarPorId(@PathVariable int id) {
        return arquivoPostagemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<ArquivoPostagem>> buscarPorPostagem(@PathVariable int postagemId) {
        return ResponseEntity.ok(arquivoPostagemService.buscarPorPostagem(postagemId));
    }

    @PostMapping("/postagem/{postagemId}")
    public ResponseEntity<ArquivoPostagem> salvar(
            @RequestBody ArquivoPostagem arquivo,
            @PathVariable int postagemId) {
        return ResponseEntity.ok(arquivoPostagemService.salvar(arquivo, postagemId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArquivoPostagem> atualizar(
            @PathVariable int id,
            @RequestBody ArquivoPostagem novoArquivo) {
        return ResponseEntity.ok(arquivoPostagemService.atualizar(id, novoArquivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable int id) {
        arquivoPostagemService.excluir(id);
        return ResponseEntity.ok("Arquivo desativado com sucesso.");
    }
}
