package br.com.fiap.techchallenge.servicocobranca.core.dtos;

public class MensagemDTOBase {
    private Long idPedido;

    public MensagemDTOBase() {
    }

    public MensagemDTOBase(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}
