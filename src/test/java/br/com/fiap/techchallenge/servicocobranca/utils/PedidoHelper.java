package br.com.fiap.techchallenge.servicocobranca.utils;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusPedidoRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.ItemPedidoRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.PedidoRequest;
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

    public static PedidoRequest criarPedidoRequest(Long clientId, Long produtoId) {
        var pedidoRequest = new PedidoRequest();
        pedidoRequest.setClienteId(clientId);
        pedidoRequest.setItens(criarItensPedidoRequest(produtoId));
        return pedidoRequest;
    }

    private static List<ItemPedidoRequest> criarItensPedidoRequest(Long produtoId) {
        var itemPedidoRequest = new ItemPedidoRequest();
        itemPedidoRequest.setProdutoId(produtoId);
        itemPedidoRequest.setQuantidade(1);
        return List.of(itemPedidoRequest);
    }

    public static PedidoDTO criaPedidoDTO() {
        ClienteDTO clienteDTO = ClienteHelper.criaClienteDTO();
        List<ItemPedidoDTO> itemsPedido = ItemPedidoHelper.criaListaItemPedidoDTO();
        return new PedidoDTO(1L, clienteDTO, itemsPedido, StatusPedidoEnum.PENDENTE_DE_PAGAMENTO, BigDecimal.valueOf(1L), LocalDateTime.now());
    }

    public static List<PedidoDTO> criaListaPedidoDTO() {
        return List.of(criaPedidoDTO());
    }

    public static PedidoRequest criaPedidoRequest() {
        return new PedidoRequest(
                1L,
                ItemPedidoHelper.criaListaItemPedidoRequest()
        );
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

    public static List<PedidoResponse> criaListaPedidoResponse() {
        PedidoResponse pedidoResponse = criaPedidoResponse();
        return List.of(pedidoResponse);
    }

    public static AtualizaStatusPedidoRequest criaAtualizaStatusPedidoRequest() {
        return new AtualizaStatusPedidoRequest(StatusPedidoEnum.RECEBIDO);
    }

}
