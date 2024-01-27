package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;

import java.util.List;

public class StatusPedidoMercadoPago {

    private Long id;

    private String status;
    private List<Pagamento> payments;

    public StatusPedidoMercadoPago() {
    }

    public StatusPedidoMercadoPago(Long id, String status, List<Pagamento> payments) {
        this.id = id;
        this.status = status;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public List<Pagamento> getPayments() {
        return payments;
    }

    private StatusCobrancaEnum toStatusCobrancaEnum() {
        return switch (this.payments.get(0).getStatus()) {
            case "approved" -> StatusCobrancaEnum.PAGO;
            case "rejected" -> StatusCobrancaEnum.RECUSADO;
            default -> null;
        };
    }

    public StatusPagamentoDTO toStatusPagamentoDTO() {
        return new StatusPagamentoDTO(this.getId(), this.getStatus(), this.toStatusCobrancaEnum());
    }
}