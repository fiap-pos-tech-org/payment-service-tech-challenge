package br.com.fiap.techchallenge.servicocobranca.core.dtos;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;

public class MensagemPagamentoPedidoDTO extends MensagemDTOBase {

    private StatusPedidoEnum status;

    public MensagemPagamentoPedidoDTO() {
    }

    public MensagemPagamentoPedidoDTO(Long idPedido, StatusPedidoEnum status) {
        super(idPedido);
        this.status = status;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }
}
