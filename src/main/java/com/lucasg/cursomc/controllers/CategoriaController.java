package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
