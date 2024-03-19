package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DadoTransacaoMercadoPagoResponse {

    @JsonProperty("qr_code_base64")
    private String qrCodeBase64;

    @JsonProperty("qr_code")
    private String qrCode;

    public DadoTransacaoMercadoPagoResponse() {
    }

    public DadoTransacaoMercadoPagoResponse(String qrCodeBase64, String qrCode) {
        this.qrCodeBase64 = qrCodeBase64;
        this.qrCode = qrCode;
    }

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }

    public void setQrCodeBase64(String qrCodeBase64) {
        this.qrCodeBase64 = qrCodeBase64;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
