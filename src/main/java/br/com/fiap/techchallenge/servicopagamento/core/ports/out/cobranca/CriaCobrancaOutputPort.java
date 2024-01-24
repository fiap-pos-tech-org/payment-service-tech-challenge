package br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;

public interface CriaCobrancaOutputPort {
    CobrancaDTO criar(CobrancaDTO cobranca);
}
