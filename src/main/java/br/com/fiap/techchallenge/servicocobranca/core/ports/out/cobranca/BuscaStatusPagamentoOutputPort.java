package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;

public interface BuscaStatusPagamentoOutputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
