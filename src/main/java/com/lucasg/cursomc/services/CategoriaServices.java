package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.repositories.CategoriaRepository;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaServices {

    private final CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
        return categoria;
    }

}
