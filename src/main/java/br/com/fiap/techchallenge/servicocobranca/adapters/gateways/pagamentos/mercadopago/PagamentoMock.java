package br.com.fiap.techchallenge.servicocobranca.adapters.gateways.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaQrCodeOutputPort;

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
