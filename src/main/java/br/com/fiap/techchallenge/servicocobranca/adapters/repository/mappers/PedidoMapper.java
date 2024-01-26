package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toPedidoDTO(PedidoResponse pedidoResponse) {
        return new PedidoDTO(pedidoResponse);
    }
}
