package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;

import java.math.BigDecimal;

public record CobrancaDTO(Long id, Long pedidoId, BigDecimal valor, StatusCobrancaEnum status, String qrCodeBase64) {
    public CobrancaDTO(Cobranca cobranca) {
        this(
            cobranca.getId(),
            cobranca.getPedidoId(),
            cobranca.getValor(),
            cobranca.getStatus(),
            cobranca.getQrCodeBase64()
        );
    }
}
