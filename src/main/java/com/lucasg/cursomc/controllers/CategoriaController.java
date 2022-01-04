package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> listar(@PathVariable Integer id) {

        Categoria categoria = categoriaService.buscar(id);

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Categoria categoria) {
        Categoria categoria1 = categoriaService.criar(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoria1.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
