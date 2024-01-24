package br.com.fiap.techchallenge.servicopagamento.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.BuscaCobrancaOutputPort;

public class BuscaCobrancaPorIdUseCase implements BuscaCobrancaPorIdInputPort {

    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public BuscaCobrancaPorIdUseCase(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }
    @Override
    public CobrancaDTO buscarPorId(Long id) {
        return buscaCobrancaOutputPort.buscarPorId(id);
    }

}
