package com.lucasg.cursomc.controllers;

import com.lucasg.cursomc.domain.Cidade;
import com.lucasg.cursomc.domain.Estado;
import com.lucasg.cursomc.dto.CidadeDTO;
import com.lucasg.cursomc.dto.EstadoDTO;
import com.lucasg.cursomc.services.CidadeService;
import com.lucasg.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final EstadoService estadoService;
    private final CidadeService cidadeService;

    @Autowired
    public EstadoController(EstadoService service, CidadeService cidadeService) {
        this.estadoService = service;
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = estadoService.findAll();
        List<EstadoDTO> listDto = list.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @GetMapping("/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> cidades = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> cidadesDTO = cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(cidadesDTO);
    }
}
