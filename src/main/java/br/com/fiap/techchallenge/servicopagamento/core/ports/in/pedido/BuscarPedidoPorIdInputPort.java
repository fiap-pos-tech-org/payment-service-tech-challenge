package br.com.fiap.techchallenge.servicopagamento.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(Long id);
}
