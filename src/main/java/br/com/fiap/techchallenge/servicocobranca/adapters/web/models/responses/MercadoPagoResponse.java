package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MercadoPagoResponse {

    @JsonProperty("point_of_interaction")
    private PontoInteracaoMercadoPagoResponse pontoInteracaoMercadoPagoResponse;

    public MercadoPagoResponse() {
    }

    public MercadoPagoResponse(PontoInteracaoMercadoPagoResponse pontoInteracaoMercadoPagoResponse) {
        this.pontoInteracaoMercadoPagoResponse = pontoInteracaoMercadoPagoResponse;
    }

    public PontoInteracaoMercadoPagoResponse getPontoInteracaoMercadoPagoResponse() {
        return pontoInteracaoMercadoPagoResponse;
    }

    public void setPontoInteracaoMercadoPagoResponse(PontoInteracaoMercadoPagoResponse pontoInteracaoMercadoPagoResponse) {
        this.pontoInteracaoMercadoPagoResponse = pontoInteracaoMercadoPagoResponse;
    }

}
