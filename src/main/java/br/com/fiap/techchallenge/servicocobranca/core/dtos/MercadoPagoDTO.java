package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.MercadoPagoResponse;

public record MercadoPagoDTO(String qrCodeBase64, String qrCode) {

    public MercadoPagoDTO(MercadoPagoResponse mercadoPagoResponse) {
        this(mercadoPagoResponse
                        .getPontoInteracaoMercadoPagoResponse()
                        .getDadoTransacaoMercadoPagoResponse()
                        .getQrCodeBase64(),
                mercadoPagoResponse
                        .getPontoInteracaoMercadoPagoResponse()
                        .getDadoTransacaoMercadoPagoResponse()
                        .getQrCode());
    }

}
