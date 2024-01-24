package br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.AtualizaStatusCobrancaDTO;

public interface AtualizaStatusCobrancaOutputPort {
    CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn);
}
