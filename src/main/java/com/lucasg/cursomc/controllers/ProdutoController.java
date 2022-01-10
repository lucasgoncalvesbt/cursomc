package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.controllers.utils.URL;
import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.domain.Produto;
import com.lucasg.cursomc.dto.CategoriaDTO;
import com.lucasg.cursomc.dto.ProdutoDTO;
import com.lucasg.cursomc.services.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> find(@PathVariable Integer id) {

        Produto produto = produtoService.find(id);

        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> produtos = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtosDTO = produtos.map(ProdutoDTO::new);
        return ResponseEntity.ok(produtosDTO);
    }

}
