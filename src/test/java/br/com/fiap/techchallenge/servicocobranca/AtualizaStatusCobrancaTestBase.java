package br.com.fiap.techchallenge.servicocobranca;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;

public class AtualizaStatusCobrancaTestBase {

    private AtualizaStatusCobrancaTestBase() {
    }

    public static AtualizaStatusCobrancaRequest criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum statusCobrancaEnum) {
        return new AtualizaStatusCobrancaRequest(statusCobrancaEnum);
    }

}
