package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;

public interface BuscaCobrancaOutputPort {
    CobrancaDTO buscarPorId(Long id);

    CobrancaDTO buscarPorPedidoId(Long pedidoId);

    boolean pedidoPossuiCobranca(Long pedidoId);
}
