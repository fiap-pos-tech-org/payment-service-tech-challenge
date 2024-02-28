package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WebhookDataRequest {

    @Schema(example = "3")
    @NotNull
    @NotBlank
    private Long id;

    public WebhookDataRequest() {
    }

    public WebhookDataRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
