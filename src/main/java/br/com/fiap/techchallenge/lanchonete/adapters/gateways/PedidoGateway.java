package br.com.fiap.techchallenge.lanchonete.adapters.gateways;

import br.com.fiap.techchallenge.lanchonete.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.lanchonete.adapters.web.handlers.ErrorDetails;
import br.com.fiap.techchallenge.lanchonete.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.lanchonete.core.domain.exceptions.NotFoundException;
import br.com.fiap.techchallenge.lanchonete.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PedidoGateway implements BuscarPedidoPorIdOutputPort {

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
        var mapper = JsonMapper.builder()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndAddModules()
                .build();

        var request = new Request.Builder()
                .url(urlApiPedidos + id)
                .build();

        try {
            var response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                var errorDetails = mapper.readValue(response.body().byteStream(),
                        ErrorDetails.class);
                throw new NotFoundException(Objects.nonNull(errorDetails.message()) ? errorDetails.message() : response.message());
            }

            var pedidoResponse = mapper.readValue(response.body().byteStream(),
                    PedidoResponse.class);

            return pedidoMapper.toPedidoDTO(pedidoResponse);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
