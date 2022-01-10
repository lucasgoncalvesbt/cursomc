package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.Categoria;
import com.lucasg.cursomc.domain.Pedido;
import com.lucasg.cursomc.domain.Produto;
import com.lucasg.cursomc.repositories.CategoriaRepository;
import com.lucasg.cursomc.repositories.PedidoRepository;
import com.lucasg.cursomc.repositories.ProdutoRepository;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

}
