package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Cidade;
import com.lucasg.cursomc.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<Cidade> findByEstado(Integer estadoId) {
        return cidadeRepository.findCidades(estadoId);
    }

}
