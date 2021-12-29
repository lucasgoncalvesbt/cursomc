package com.lucasg.cursomc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ItemPedidoPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedidoPK that = (ItemPedidoPK) o;

        if (pedido != null ? !pedido.equals(that.pedido) : that.pedido != null) return false;
        return produto != null ? produto.equals(that.produto) : that.produto == null;
    }

    @Override
    public int hashCode() {
        int result = pedido != null ? pedido.hashCode() : 0;
        result = 31 * result + (produto != null ? produto.hashCode() : 0);
        return result;
    }
}
