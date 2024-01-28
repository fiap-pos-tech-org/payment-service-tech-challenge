package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookDataRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookStatusCobrancaRequest;

public class WebhookStatusCobrancaHelper {

    private WebhookStatusCobrancaHelper() {
    }

    public static WebhookStatusCobrancaRequest criarWebhookStatusCobrancaRequest(String action, Long id) {
        return new WebhookStatusCobrancaRequest(action, criarWebhookDataRequest(id));
    }

    private static WebhookDataRequest criarWebhookDataRequest(Long id) {
        return new WebhookDataRequest(id);
    }

}
