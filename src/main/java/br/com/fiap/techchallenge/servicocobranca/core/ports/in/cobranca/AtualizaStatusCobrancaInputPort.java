package br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.AtualizaStatusCobrancaDTO;

public interface AtualizaStatusCobrancaInputPort {
    CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn);
}
