package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.MercadoPagoDTO;

public interface CriaCobrancaMercadoPagoOutputPort {
    MercadoPagoDTO criar(Long pedidoId);
}
