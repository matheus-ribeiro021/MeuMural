package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.GrupoDTORequest;
import com.meumural.projetobackend.dto.response.GrupoDTOResponse;
import com.meumural.projetobackend.service.GrupoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/grupo")
@CrossOrigin(origins = "*")
public class GrupoController {

    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<GrupoDTOResponse>> listarGrupos() {
        return ResponseEntity.ok(grupoService.retornarGrupos());
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<GrupoDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        GrupoDTOResponse dto = grupoService.retornarGrupoPorId(id);
        if (dto == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/criar")
    public ResponseEntity<GrupoDTOResponse> criarGrupo(@Valid @RequestBody GrupoDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(grupoService.salvarGrupo(dto));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<GrupoDTOResponse> atualizarGrupo(
            @PathVariable("id") Integer id,
            @Valid @RequestBody GrupoDTORequest dto) {
        return ResponseEntity.ok(grupoService.atualizarGrupo(id, dto));
    }

    @DeleteMapping("/apagar/{id}")
    public ResponseEntity<Void> apagarGrupo(@PathVariable("id") Integer id) {
        grupoService.excluirGrupo(id);
        return ResponseEntity.noContent().build();
    }
}
