package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;

public interface CriaCobrancaOutputPort {
    CobrancaDTO criar(CobrancaDTO cobranca);
}
