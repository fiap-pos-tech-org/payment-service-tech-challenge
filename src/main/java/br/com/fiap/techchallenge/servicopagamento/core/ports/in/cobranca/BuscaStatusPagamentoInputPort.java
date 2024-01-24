package br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.StatusPagamentoDTO;

public interface BuscaStatusPagamentoInputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
