package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;

public record StatusPagamentoDTO(
        Long id,
        String statusPedido,
        StatusCobrancaEnum statusPagamento
) {
}
