package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;

public class CobrancaResponse {

    private Long id;
    private Long pedidoId;
    private StatusCobrancaEnum status;
    private BigDecimal valor;
    private String qrCodeBase64;

    public CobrancaResponse(Long id, Long pedidoId, StatusCobrancaEnum status, BigDecimal valor, String qrCodeBase64) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.qrCodeBase64 = qrCodeBase64;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public StatusCobrancaEnum getStatus() {
        return status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @JsonGetter(value = "qrCode")
    public String getQrCodeBase64() {
        return qrCodeBase64;
    }
}
