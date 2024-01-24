package br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.QrCode;

import java.math.BigDecimal;

public interface CriaQrCodeOutputPort {
    QrCode criar(Long pedidoId, BigDecimal valor);
}
