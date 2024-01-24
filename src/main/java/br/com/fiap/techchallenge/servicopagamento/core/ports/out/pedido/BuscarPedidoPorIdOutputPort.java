package br.com.fiap.techchallenge.servicopagamento.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicopagamento.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdOutputPort {

    PedidoDTO buscarPorId(Long id);
}
