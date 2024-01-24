package br.com.fiap.techchallenge.servicopagamento;

import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusCobrancaEnum;

public class AtualizaStatusCobrancaTestBase {

    private AtualizaStatusCobrancaTestBase() {
    }

    public static AtualizaStatusCobrancaRequest criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum statusCobrancaEnum) {
        return new AtualizaStatusCobrancaRequest(statusCobrancaEnum);
    }

}
