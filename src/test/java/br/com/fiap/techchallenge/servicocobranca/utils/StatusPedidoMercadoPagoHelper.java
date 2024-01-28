package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.pagamentos.mercadopago.Pagamento;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.pagamentos.mercadopago.StatusPedidoMercadoPago;

import java.util.List;

public class StatusPedidoMercadoPagoHelper {

    private StatusPedidoMercadoPagoHelper() {
    }

    public static StatusPedidoMercadoPago criaStatusPedidoMercadoPago() {
        var pagamento = new Pagamento(1L, "approved");
        return new StatusPedidoMercadoPago(1L, "approved", List.of(pagamento));
    }

}
