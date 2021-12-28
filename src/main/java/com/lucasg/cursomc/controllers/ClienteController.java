package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.services.ClienteServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteServices ClienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listar(@PathVariable Integer id) {

        Cliente cliente = ClienteService.buscar(id);

        return ResponseEntity.ok(cliente);
    }

}
