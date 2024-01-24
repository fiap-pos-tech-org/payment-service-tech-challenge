package br.com.fiap.techchallenge.servicopagamento.adapters.gateways.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.CriaQrCodeOutputPort;

import java.math.BigDecimal;

public class PagamentoMock implements CriaQrCodeOutputPort {
    @Override
    public QrCode criar(Long pedidoId, BigDecimal valor) {
        return new QrCode(
                generateQrCode(pedidoId, valor)
        );
    }

    private String generateQrCode(Long pedidoId, BigDecimal valor) {
        return "{pedidoId:"+pedidoId+",valor:"+valor+"}";
    }
}
