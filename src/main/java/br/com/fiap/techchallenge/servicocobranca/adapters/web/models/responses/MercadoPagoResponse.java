package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses;

public class MercadoPagoResponse {

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
