package br.com.fiap.techchallenge.servicocobranca.core.domain.entities;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;

import java.math.BigDecimal;

public class Cobranca {

    private Long id;
    private Long pedidoId;
    private StatusCobrancaEnum status;
    private BigDecimal valor;
    private String qrCodeBase64;

    public Cobranca(Long pedidoId, StatusCobrancaEnum status, BigDecimal valor, String qrCodeBase64) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.qrCodeBase64 = qrCodeBase64;
        this.valor = valor;
    }

    public Cobranca(Long id, Long pedidoId, StatusCobrancaEnum status, BigDecimal valor, String qrCodeBase64) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.qrCodeBase64 = qrCodeBase64;
        this.valor = valor;
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

    public String getQrCodeBase64() {
        return qrCodeBase64;
    }
}
