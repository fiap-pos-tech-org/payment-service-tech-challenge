package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;

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
