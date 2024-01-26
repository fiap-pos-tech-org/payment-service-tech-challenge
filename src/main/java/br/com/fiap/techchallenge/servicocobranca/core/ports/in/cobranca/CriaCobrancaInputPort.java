package br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;

public interface CriaCobrancaInputPort {
    CobrancaDTO criar(CriaCobrancaDTO cobrancaIn);
}
