package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.ClienteDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.ItemPedidoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoHelper {

    private PedidoHelper() {
    }

    public static PedidoDTO criaPedidoDTO() {
        ClienteDTO clienteDTO = ClienteHelper.criaClienteDTO();
        List<ItemPedidoDTO> itemsPedido = ItemPedidoHelper.criaListaItemPedidoDTO();
        return new PedidoDTO(1L, clienteDTO, itemsPedido, StatusPedidoEnum.PENDENTE_DE_PAGAMENTO, BigDecimal.valueOf(1L), LocalDateTime.now());
    }

    public static PedidoResponse criaPedidoResponse() {
        return new PedidoResponse(
                1L,
                "Cliente",
                ItemPedidoHelper.criaListaItemPedidoResponse(),
                StatusPedidoEnum.PENDENTE_DE_PAGAMENTO,
                BigDecimal.valueOf(1L),
                LocalDateTime.now()
        );
    }

}
