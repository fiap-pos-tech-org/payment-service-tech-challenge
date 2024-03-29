package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WebhookStatusCobrancaRequest {

    @Schema(example = "approved")
    @NotNull
    @NotBlank
    private String action;

    @NotNull
    private WebhookDataRequest data;

    public WebhookStatusCobrancaRequest() {
    }

    public WebhookStatusCobrancaRequest(String action, WebhookDataRequest data) {
        this.action = action;
        this.data = data;
    }


    public String getAction() {
        return action;
    }

    public WebhookDataRequest getData() {
        return data;
    }
}