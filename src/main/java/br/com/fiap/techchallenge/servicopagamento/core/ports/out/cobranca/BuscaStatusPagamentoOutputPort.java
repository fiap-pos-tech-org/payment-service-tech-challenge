package br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.StatusPagamentoDTO;

public interface BuscaStatusPagamentoOutputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
