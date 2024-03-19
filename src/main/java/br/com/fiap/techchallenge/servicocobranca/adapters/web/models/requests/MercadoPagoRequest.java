package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class MercadoPagoRequest {

    @Schema(example = "1")
    @NotNull(message = "O campo pedidoId é obrigatório")
    private Long pedidoId;

    public MercadoPagoRequest() {
    }

    public MercadoPagoRequest(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

}
