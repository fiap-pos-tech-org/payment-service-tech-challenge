package br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;

public interface BuscaCobrancaOutputPort {
    CobrancaDTO buscarPorId(Long id);

    CobrancaDTO buscarPorPedidoId(Long pedidoId);

    boolean pedidoPossuiCobranca(Long pedidoId);
}
