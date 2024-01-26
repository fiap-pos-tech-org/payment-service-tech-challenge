package br.com.fiap.techchallenge.servicocobranca.adapters.gateways;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoGateway extends GatewayBase implements BuscarPedidoPorIdOutputPort, AtualizaStatusPedidoOutputPort {

    private final OkHttpClient httpClient;
    private final PedidoMapper pedidoMapper;

    @Value("${pedidos.api.url}")
    private String urlApiPedidos;

    public PedidoGateway(OkHttpClient httpClient, PedidoMapper pedidoMapper) {
        this.httpClient = httpClient;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoDTO buscarPorId(Long id) {
        var request = new Request.Builder()
                .url(String.format("%s/%s", urlApiPedidos, id))
                .build();
        var pedidoResponse = newCall(request, PedidoResponse.class);
        return pedidoMapper.toPedidoDTO(pedidoResponse);
    }

    @Override
    public PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status) {
        buscarPorId(id);
        var atualizaStatusPedidoRequest = new AtualizaStatusPedidoRequest(status);

        String jsonBody;
        try {
            jsonBody = getJsonMapper()
                    .writeValueAsString(atualizaStatusPedidoRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        var requestBody = RequestBody.create(MediaType.parse("application/json"), jsonBody);
        var request = new Request.Builder()
                .url(String.format("%s/%s/%s", urlApiPedidos, id, "status"))
                .patch(requestBody)
                .build();

        var pedidoResponse = newCall(request, PedidoResponse.class);
        return pedidoMapper.toPedidoDTO(pedidoResponse);
    }

    @Override
    protected OkHttpClient getHttpClient() {
        return this.httpClient;
    }

}
