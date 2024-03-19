package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import java.math.BigDecimal;

public record CriaCobrancaDTO(Long pedidoId, Long idCliente, BigDecimal valorTotal) {

    public CriaCobrancaDTO(MensagemPedidoPagamentoDTO mensagemPedidoPagamentoDTO) {
        this(mensagemPedidoPagamentoDTO.getIdPedido(), mensagemPedidoPagamentoDTO.getIdCliente(),
                mensagemPedidoPagamentoDTO.getValorTotal());
    }
}
