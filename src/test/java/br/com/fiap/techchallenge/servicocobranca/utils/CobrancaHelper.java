package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.CobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookDataRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CobrancaHelper {

    private CobrancaHelper() {
    }

    public static CobrancaRequest criarCobrancaRequest(Long pedidoId) {
        var cobrancaRequest = new CobrancaRequest();
        cobrancaRequest.setPedidoId(pedidoId);
        return cobrancaRequest;
    }

    public static Cobranca criaCobranca() {
        return new Cobranca(1L, 1L, StatusCobrancaEnum.PENDENTE, BigDecimal.valueOf(1),
                "1234", LocalDateTime.now(), LocalDateTime.now());
    }

    public static CobrancaDTO criaCobrancaDTO() {
        return new CobrancaDTO(1L, 1L, BigDecimal.valueOf(1), StatusCobrancaEnum.PENDENTE, new QrCode("1234"));
    }

    public static CobrancaResponse criaCobrancaResponse() {
        CobrancaDTO cobrancaDTO = criaCobrancaDTO();
        return new CobrancaResponse(
                1L,
                cobrancaDTO.pedidoId(),
                cobrancaDTO.status(),
                cobrancaDTO.valor(),
                cobrancaDTO.qrCode()
        );
    }

    public static CobrancaRequest criaCobrancaRequest() {
        return new CobrancaRequest(1L);
    }

    public static StatusPagamentoDTO criaStatusPagamentoDTO() {
        return new StatusPagamentoDTO(1L, "PAGO", StatusCobrancaEnum.PAGO);
    }

    public static WebhookStatusCobrancaRequest criaWebhookStatusCobrancaRequest() {
        return new WebhookStatusCobrancaRequest("PAYMENT_STATUS_CHANGED", new WebhookDataRequest(1L));
    }

    public static WebhookStatusCobrancaRequest criaWebhookStatusCobrancaRequest(String action) {
        return new WebhookStatusCobrancaRequest(action, new WebhookDataRequest(1L));
    }

}
