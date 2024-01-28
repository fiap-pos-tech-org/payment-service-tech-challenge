package br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;

public interface BuscaCobrancaPorPedidoIdInputPort {
    CobrancaDTO buscarPorPedidoId(Long pedidoId);
}
