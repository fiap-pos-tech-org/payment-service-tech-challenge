package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;

public class AtualizaStatusCobrancaHelper {

    private AtualizaStatusCobrancaHelper() {
    }

    public static AtualizaStatusCobrancaRequest criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum statusCobrancaEnum) {
        return new AtualizaStatusCobrancaRequest(statusCobrancaEnum);
    }

}
