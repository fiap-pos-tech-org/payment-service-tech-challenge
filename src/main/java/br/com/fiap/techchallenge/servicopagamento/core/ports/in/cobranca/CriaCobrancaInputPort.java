package br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;

public interface CriaCobrancaInputPort {
    CobrancaDTO criar(CriaCobrancaDTO cobrancaIn);
}
