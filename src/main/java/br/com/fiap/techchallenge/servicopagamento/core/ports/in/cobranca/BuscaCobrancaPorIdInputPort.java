package br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;

public interface BuscaCobrancaPorIdInputPort {
    CobrancaDTO buscarPorId(Long id);
}
