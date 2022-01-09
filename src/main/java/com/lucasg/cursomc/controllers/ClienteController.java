package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.dto.ClienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final com.lucasg.cursomc.services.ClienteService clienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {

        Cliente cliente = clienteService.find(id);

        return ResponseEntity.ok(cliente);
    }

//    @PostMapping
//    public ResponseEntity<Void> create(@Valid @RequestBody ClienteDTO clienteDTO) {
//        Cliente cliente1 = clienteService.create(clienteService.fromDTO(clienteDTO));
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}").buildAndExpand(cliente1.getId()).toUri();
//
//        return ResponseEntity.created(uri).build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO DTO, @PathVariable Integer id) {
        Cliente cliente = clienteService.fromDTO(DTO);
        cliente.setId(id);
        clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(clientesDTO);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Page<Cliente> clientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clientesDTO = clientes.map(ClienteDTO::new);
        return ResponseEntity.ok(clientesDTO);
    }

}
