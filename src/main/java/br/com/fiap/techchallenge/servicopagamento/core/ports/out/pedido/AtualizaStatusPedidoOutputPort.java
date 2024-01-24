package br.com.fiap.techchallenge.servicopagamento.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusPedidoEnum;

public interface AtualizaStatusPedidoOutputPort {
    PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status);
}
