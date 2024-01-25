package br.com.fiap.techchallenge.servicocobranca;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.CobrancaRequest;

public class CobrancaTestBase {

    private CobrancaTestBase() {
    }

    public static CobrancaRequest criarCobrancaRequest(Long pedidoId) {
        var cobrancaRequest = new CobrancaRequest();
        cobrancaRequest.setPedidoId(pedidoId);
        return cobrancaRequest;
    }

}
