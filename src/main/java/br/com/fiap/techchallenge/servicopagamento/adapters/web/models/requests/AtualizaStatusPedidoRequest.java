package br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests;

import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusPedidoEnum;
import jakarta.validation.constraints.NotNull;

public class AtualizaStatusPedidoRequest {
    private StatusPedidoEnum status;

    public AtualizaStatusPedidoRequest() {
    }

    public AtualizaStatusPedidoRequest(StatusPedidoEnum status) {
        this.status = status;
    }

    @NotNull(message = "O campo 'status' é obrigatório")
    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }

}
