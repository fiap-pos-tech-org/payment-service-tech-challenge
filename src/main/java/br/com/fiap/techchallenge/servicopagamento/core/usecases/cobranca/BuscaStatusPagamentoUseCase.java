package br.com.fiap.techchallenge.servicopagamento.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.StatusPagamentoDTO;
import br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.BuscaStatusPagamentoOutputPort;

public class BuscaStatusPagamentoUseCase implements BuscaStatusPagamentoInputPort {

    private final BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort;

    public BuscaStatusPagamentoUseCase(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        this.buscaStatusPagamentoOutputPort = buscaStatusPagamentoOutputPort;
    }

    @Override
    public StatusPagamentoDTO buscaStatus(Long id) {
        return buscaStatusPagamentoOutputPort.buscaStatus(id);
    }
}
