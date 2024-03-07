package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ItemPedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.ItemPedidoDTO;

import java.math.BigDecimal;
import java.util.List;

public class ItemPedidoHelper {

    private ItemPedidoHelper() {
    }

    public static List<ItemPedidoDTO> criaListaItemPedidoDTO() {
        var itemPedidoDTO = new ItemPedidoDTO(1L, "X-Tudo", "X-Tudo Monstrão", BigDecimal.valueOf(1L), 1);
        return List.of(itemPedidoDTO);
    }

    public static List<ItemPedidoRequest> criaListaItemPedidoRequest() {
        return List.of(new ItemPedidoRequest(1L, 1));
    }

    public static List<ItemPedidoResponse> criaListaItemPedidoResponse() {
        return List.of(criaItemPedidoResponse());
    }

    private static ItemPedidoResponse criaItemPedidoResponse() {
        return new ItemPedidoResponse(
                "Produto",
                "Descrição",
                BigDecimal.valueOf(1L),
                1,
                BigDecimal.valueOf(1L)
        );
    }
}
