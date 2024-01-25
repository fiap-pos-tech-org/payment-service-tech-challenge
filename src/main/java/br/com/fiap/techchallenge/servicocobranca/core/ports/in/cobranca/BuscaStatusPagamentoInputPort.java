package br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;

public interface BuscaStatusPagamentoInputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
