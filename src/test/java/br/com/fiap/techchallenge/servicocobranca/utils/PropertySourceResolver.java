package br.com.fiap.techchallenge.servicocobranca.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertySourceResolver {

    @Value("${mercadopago.api.url}")
    private String urlMercadoPagoApiPagamentos;

    @Value("${mercadopago.api.token}")
    private String token;

    @Value("${pedidos.api.url}")
    private String urlApiPedidos;

    @Value("${mercadopago.pagamento.api.url}")
    private String urlApiMercadoPago;

    public String getUrlMercadoPagoApiPagamentos() {
        return urlMercadoPagoApiPagamentos;
    }

    public String getToken() {
        return token;
    }

    public String getUrlApiPedidos() {
        return urlApiPedidos;
    }

    public String getUrlApiMercadoPago() {
        return urlApiMercadoPago;
    }
}
