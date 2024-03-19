package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.DadoTransacaoMercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.MercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PontoInteracaoMercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.MercadoPagoDTO;

public class MercadoPagoHelper {

    private MercadoPagoHelper() {
    }

    public static MercadoPagoDTO criaMercadoPagoDTO() {
        return new MercadoPagoDTO("iVBORw0KGgoAAAANSUhEUgAABRQAAAUUCAYAAACu5p7oAAAABGdBTUEAALGPC",
                "00020126600014br");
    }

    public static MercadoPagoResponse criaMercadoPagoResponse() {
        var mercadoPagoDTO = criaMercadoPagoDTO();
        var dadoTransacaoMercadoPagoResponse = new DadoTransacaoMercadoPagoResponse(mercadoPagoDTO.qrCodeBase64(),
                mercadoPagoDTO.qrCode());
        var pontoInteracaoMercadoPagoResponse = new PontoInteracaoMercadoPagoResponse(dadoTransacaoMercadoPagoResponse);
        return new MercadoPagoResponse(pontoInteracaoMercadoPagoResponse);
    }

}
