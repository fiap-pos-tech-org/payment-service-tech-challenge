package br.com.fiap.techchallenge.servicopagamento.adapters.gateways;

import br.com.fiap.techchallenge.servicopagamento.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicopagamento.adapters.web.handlers.ErrorDetails;
import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopagamento.core.domain.exceptions.NotFoundException;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PedidoGateway implements BuscarPedidoPorIdOutputPort, AtualizaStatusPedidoOutputPort {

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
        return newCall(request);
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

        return newCall(request);
    }

    public PedidoDTO newCall(Request request) {
        var mapper = getJsonMapper();

        try {
            var response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                var errorDetails = mapper.readValue(response.body().byteStream(),
                        ErrorDetails.class);
                throw new NotFoundException(Objects.nonNull(errorDetails.message()) ? errorDetails.message() : response.message());
            }

            var pedidoResponse = mapper.readValue(response.body().byteStream(), PedidoResponse.class);
            return pedidoMapper.toPedidoDTO(pedidoResponse);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private JsonMapper getJsonMapper() {
        return JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndAddModules()
                .build();
    }

}
