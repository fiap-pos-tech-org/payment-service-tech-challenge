package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.AtualizaStatusCobrancaDTO;

public interface AtualizaStatusCobrancaOutputPort {
    CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn);
}
