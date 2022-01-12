package com.lucasg.cursomc.services;

import com.lucasg.cursomc.domain.ItemPedido;
import com.lucasg.cursomc.domain.PagamentoComBoleto;
import com.lucasg.cursomc.domain.Pedido;
import com.lucasg.cursomc.domain.enums.EstadoPagamento;
import com.lucasg.cursomc.repositories.ItemPedidoRepository;
import com.lucasg.cursomc.repositories.PagamentoRepository;
import com.lucasg.cursomc.repositories.PedidoRepository;
import com.lucasg.cursomc.services.exceptions.ObjectNotFoundExeception;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ClienteService clienteService;
    private final BoletoService boletoService;
    private final ProdutoService produtoService;
    private final EmailService emailService;

    public Pedido find(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundExeception("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido create(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
        pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido = pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido ip : pedido.getItemsPedidos()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(pedido);

        }
        itemPedidoRepository.saveAll(pedido.getItemsPedidos());
        emailService.sendOrderConfirmationEmail(pedido);
        return pedido;
    }

}
