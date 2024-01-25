package br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequest {

    private Long clienteId;
    private List<ItemPedidoRequest> itens;

    public PedidoRequest() {
    }

    public PedidoRequest(Long clienteId, List<ItemPedidoRequest> itens) {
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public PedidoRequest(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }

    public Long getClienteId() {
        return clienteId;
    }

    @NotNull(message = "O campo 'itens' é obrigatório")
    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }

}
