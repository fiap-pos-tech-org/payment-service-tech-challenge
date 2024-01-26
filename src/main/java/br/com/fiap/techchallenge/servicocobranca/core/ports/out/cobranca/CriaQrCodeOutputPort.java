package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.QrCode;

import java.math.BigDecimal;

public interface CriaQrCodeOutputPort {
    QrCode criar(Long pedidoId, BigDecimal valor);
}
