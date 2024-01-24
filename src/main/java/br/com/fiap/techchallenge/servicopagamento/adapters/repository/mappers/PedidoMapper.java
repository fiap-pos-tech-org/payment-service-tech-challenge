package br.com.fiap.techchallenge.servicopagamento.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoDTO toPedidoDTO(PedidoResponse pedidoResponse) {
        return new PedidoDTO(pedidoResponse);
    }
}
