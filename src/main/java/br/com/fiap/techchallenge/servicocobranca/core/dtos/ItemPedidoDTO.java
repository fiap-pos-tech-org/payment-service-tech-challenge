package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ItemPedidoResponse;

import java.math.BigDecimal;

public record ItemPedidoDTO(
        Long produtoId,
        String produtoNome,
        String produtoDescricao,
        BigDecimal valorUnitario,
        Integer quantidade
) {

    public ItemPedidoDTO(ItemPedidoResponse itemPedidoResponse) {
        this(null, itemPedidoResponse.getProdutoNome(), itemPedidoResponse.getProdutoDescricao(),
                itemPedidoResponse.getValorUnitario(), itemPedidoResponse.getQuantidade());
    }

    public BigDecimal getValorTotal() {
        return BigDecimal.valueOf(quantidade).multiply(valorUnitario);
    }
}
