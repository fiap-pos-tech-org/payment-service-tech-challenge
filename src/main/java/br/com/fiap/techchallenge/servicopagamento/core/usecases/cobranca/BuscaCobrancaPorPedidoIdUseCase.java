package br.com.fiap.techchallenge.servicopagamento.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.BuscaCobrancaOutputPort;

public class BuscaCobrancaPorPedidoIdUseCase implements BuscaCobrancaPorPedidoIdInputPort {

    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public BuscaCobrancaPorPedidoIdUseCase(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }

    @Override
    public CobrancaDTO buscarPorPedidoId(Long pedidoId) {
        return buscaCobrancaOutputPort.buscarPorPedidoId(pedidoId);
    }
}
