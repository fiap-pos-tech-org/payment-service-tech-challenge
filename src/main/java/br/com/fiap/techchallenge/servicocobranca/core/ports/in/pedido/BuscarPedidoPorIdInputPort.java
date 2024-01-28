package br.com.fiap.techchallenge.servicocobranca.core.ports.in.pedido;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(Long id);
}
