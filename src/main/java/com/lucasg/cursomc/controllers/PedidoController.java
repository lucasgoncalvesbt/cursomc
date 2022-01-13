package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Pedido;
import com.lucasg.cursomc.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {

        Pedido pedido = pedidoService.find(id);

        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Pedido pedido) {
        pedido = pedidoService.create(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ) {
        Page<Pedido> pedidos = pedidoService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok(pedidos);
    }

}
