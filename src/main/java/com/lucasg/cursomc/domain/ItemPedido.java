package com.lucasg.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ItemPedido {

    @JsonIgnore
    @EmbeddedId
    private ItemPedidoPK id = new ItemPedidoPK();

    private Double desconto;
    private Integer quantidade;
    private Double preco;

    public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
        this.id.setPedido(pedido);
        this.id.setProduto(produto);
        this.desconto = desconto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public double getSubTotal(){
        return (preco - desconto) * quantidade;
    }

    @JsonIgnore
    public Pedido getPedido() {
        return this.id.getPedido();
    }

    public void setPedido(Pedido pedido) {
        this.id.setPedido(pedido);
    }

    public Produto getProduto() {
        return this.id.getProduto();
    }

    public void setProduto(Produto produto) {
        this.id.setProduto(produto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido that = (ItemPedido) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
