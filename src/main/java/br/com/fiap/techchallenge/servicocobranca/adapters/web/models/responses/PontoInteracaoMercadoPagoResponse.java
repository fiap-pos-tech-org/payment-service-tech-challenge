package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PontoInteracaoMercadoPagoResponse {

    @JsonProperty("transaction_data")
    private DadoTransacaoMercadoPagoResponse dadoTransacaoMercadoPagoResponse;

    public PontoInteracaoMercadoPagoResponse() {
    }

    public PontoInteracaoMercadoPagoResponse(DadoTransacaoMercadoPagoResponse dadoTransacaoMercadoPagoResponse) {
        this.dadoTransacaoMercadoPagoResponse = dadoTransacaoMercadoPagoResponse;
    }

    public DadoTransacaoMercadoPagoResponse getDadoTransacaoMercadoPagoResponse() {
        return dadoTransacaoMercadoPagoResponse;
    }

    public void setDadoTransacaoMercadoPagoResponse(DadoTransacaoMercadoPagoResponse dadoTransacaoMercadoPagoResponse) {
        this.dadoTransacaoMercadoPagoResponse = dadoTransacaoMercadoPagoResponse;
    }
}
