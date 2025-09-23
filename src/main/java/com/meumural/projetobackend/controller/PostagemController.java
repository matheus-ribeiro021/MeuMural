package com.meumural.projetobackend.controller;

import com.meumural.projetobackend.dto.request.PostagemDTORequest;
import com.meumural.projetobackend.dto.response.PostagemDTOResponse;
import com.meumural.projetobackend.entity.Postagem;
import com.meumural.projetobackend.service.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/postagem")
@Tag(name = "postagem", description = "API para gerenciamento de postagens")
public class PostagemController {

}
