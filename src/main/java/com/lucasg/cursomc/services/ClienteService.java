package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Cliente;
import com.lucasg.cursomc.repositories.ClienteRepository;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente find(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

}
