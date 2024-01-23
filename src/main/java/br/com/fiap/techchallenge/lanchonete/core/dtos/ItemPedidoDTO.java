package br.com.fiap.techchallenge.lanchonete.core.dtos;

import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.ItemPedidoResponse;
import br.com.fiap.techchallenge.lanchonete.core.domain.entities.ItemPedido;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        Long produtoId,
        String produtoNome,
        String produtoDescricao,
        BigDecimal valorUnitario,
        Integer quantidade
) {

    public ItemPedidoDTO(ItemPedido itemPedido) {
        this(itemPedido.getProdutoId(), itemPedido.getProdutoNome(), itemPedido.getProdutoDescricao(),
                itemPedido.getValorUnitario(), itemPedido.getQuantidade());
    }

    public ItemPedidoDTO(ItemPedidoResponse itemPedidoResponse) {
        this(null, itemPedidoResponse.getProdutoNome(), itemPedidoResponse.getProdutoDescricao(),
                itemPedidoResponse.getValorUnitario(), itemPedidoResponse.getQuantidade());
    }

    public BigDecimal getValorTotal() {
        return BigDecimal.valueOf(quantidade).multiply(valorUnitario);
    }
}
