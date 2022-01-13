package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.dto.CategoriaDTO;
import com.lucasg.cursomc.services.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {

        Categoria categoria = categoriaService.find(id);

        return ResponseEntity.ok(categoria);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        Categoria categoria1 = categoriaService.create(categoriaService.fromDTO(categoriaDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(categoria1.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO DTO, @PathVariable Integer id) {
        Categoria categoria = categoriaService.fromDTO(DTO);
        categoria.setId(id);
        categoriaService.update(categoria);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        List<CategoriaDTO> categoriasDTO = categorias.stream().map(CategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Page<Categoria> categorias = categoriaService.findPage(page, linesPerPage, orderBy, direction);
        Page<CategoriaDTO> categoriasDTO = categorias.map(CategoriaDTO::new);
        return ResponseEntity.ok(categoriasDTO);
    }
}
