package br.com.fiap.techchallenge.servicocobranca.adapters.web.models.pagamentos.mercadopago;

public class Pagamento {

    private Long id;
    private String status;

    public Pagamento() {
    }

    public Pagamento(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
