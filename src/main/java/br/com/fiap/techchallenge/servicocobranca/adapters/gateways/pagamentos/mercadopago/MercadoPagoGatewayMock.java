package br.com.fiap.techchallenge.servicocobranca.adapters.gateways.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicocobranca.adapters.gateways.GatewayBase;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.MercadoPagoMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.MercadoPagoRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.MercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.MercadoPagoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaCobrancaMercadoPagoOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoGatewayMock extends GatewayBase implements CriaCobrancaMercadoPagoOutputPort {

    private final OkHttpClient httpClient;
    private final MercadoPagoMapper mercadoPagoMapper;
    private final String urlApiMercadoPago;

    public MercadoPagoGatewayMock(OkHttpClient httpClient, MercadoPagoMapper mercadoPagoMapper,
                                  @Value("${mercadopago.pagamento.api.url}") String urlApiMercadoPago) {
        this.httpClient = httpClient;
        this.mercadoPagoMapper = mercadoPagoMapper;
        this.urlApiMercadoPago = urlApiMercadoPago;
    }

    @Override
    public MercadoPagoDTO criar(Long pedidoId) {
        var mercadoPagoRequest = new MercadoPagoRequest(pedidoId);

        String jsonBody;
        try {
            jsonBody = getJsonMapper()
                    .writeValueAsString(mercadoPagoRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        var requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        var request = new Request.Builder()
                .url(urlApiMercadoPago)
                .post(requestBody)
                .build();

        var mercadoPagoResponse = newCall(request, MercadoPagoResponse.class);
        return mercadoPagoMapper.toMercadoPagoDTO(mercadoPagoResponse);
    }

    @Override
    protected OkHttpClient getHttpClient() {
        return this.httpClient;
    }
}
