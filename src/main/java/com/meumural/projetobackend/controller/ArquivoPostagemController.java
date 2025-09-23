package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.ArquivoPostagemDTORequest;
import com.meumural.projetobackend.dto.response.ArquivoPostagemDTOResponse;
import com.meumural.projetobackend.entity.ArquivoPostagem;
import com.meumural.projetobackend.service.ArquivoPostagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/arquivo-postagem")
@Tag(name = "arquivo-postagem", description = "API para gerenciamento de arquivos das postagens")
public class ArquivoPostagemController {

}
