package br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;

public interface BuscaCobrancaPorPedidoIdInputPort {
    CobrancaDTO buscarPorPedidoId(Long pedidoId);
}
