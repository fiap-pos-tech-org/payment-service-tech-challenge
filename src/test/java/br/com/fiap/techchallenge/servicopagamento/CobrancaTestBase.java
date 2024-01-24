package br.com.fiap.techchallenge.servicopagamento;

import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests.CobrancaRequest;

public class CobrancaTestBase {

    private CobrancaTestBase() {
    }

    public static CobrancaRequest criarCobrancaRequest(Long pedidoId) {
        var cobrancaRequest = new CobrancaRequest();
        cobrancaRequest.setPedidoId(pedidoId);
        return cobrancaRequest;
    }

}
