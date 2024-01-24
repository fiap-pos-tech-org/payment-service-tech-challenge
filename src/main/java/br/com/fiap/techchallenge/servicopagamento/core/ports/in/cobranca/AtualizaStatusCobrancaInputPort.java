package br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.AtualizaStatusCobrancaDTO;

public interface AtualizaStatusCobrancaInputPort {
    CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn);
}
