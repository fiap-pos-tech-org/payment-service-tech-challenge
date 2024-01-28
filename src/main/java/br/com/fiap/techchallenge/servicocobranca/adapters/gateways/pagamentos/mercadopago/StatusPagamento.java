package br.com.fiap.techchallenge.servicocobranca.adapters.gateways.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicocobranca.adapters.gateways.GatewayBase;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.pagamentos.mercadopago.StatusPedidoMercadoPago;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaStatusPagamentoOutputPort;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class StatusPagamento extends GatewayBase implements BuscaStatusPagamentoOutputPort {

    private final OkHttpClient httpClient;
    private final String urlMercadoPagoApiPagamentos;
    private final String token;

    public StatusPagamento(OkHttpClient httpClient,
                           @Value("${mercadopago.api.url}") String urlMercadoPagoApiPagamentos,
                           @Value("${mercadopago.api.token}") String token) {
        this.httpClient = httpClient;
        this.urlMercadoPagoApiPagamentos = urlMercadoPagoApiPagamentos;
        this.token = token;
    }

    @Override
    public StatusPagamentoDTO buscaStatus(Long id) {
        var request = new Request.Builder()
                .url(urlMercadoPagoApiPagamentos + id)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        return newCall(request, StatusPedidoMercadoPago.class)
                .toStatusPagamentoDTO();
    }

    @Override
    protected OkHttpClient getHttpClient() {
        return this.httpClient;
    }
}
