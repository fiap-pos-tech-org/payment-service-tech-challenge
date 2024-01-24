package br.com.fiap.techchallenge.servicopagamento.core.dtos;

import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusCobrancaEnum;

public record StatusPagamentoDTO(
        Long id,
        String statusPedido,
        StatusCobrancaEnum statusPagamento
) {
}
