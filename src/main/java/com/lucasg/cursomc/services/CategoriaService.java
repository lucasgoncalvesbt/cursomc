package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.repositories.CategoriaRepository;
import com.lucasg.cursomc.services.exceptions.DataIntegrityExeception;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria create(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        find(categoria.getId());
        return categoriaRepository.save(categoria);
    }

    public void delete(Integer id) {
        find(id);

        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityExeception("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }
}
