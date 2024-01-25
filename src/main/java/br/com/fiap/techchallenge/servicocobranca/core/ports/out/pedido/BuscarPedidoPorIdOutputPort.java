package br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;

public interface BuscarPedidoPorIdOutputPort {

    PedidoDTO buscarPorId(Long id);
}
