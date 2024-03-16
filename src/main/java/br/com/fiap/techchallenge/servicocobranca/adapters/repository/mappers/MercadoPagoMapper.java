package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.MercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.MercadoPagoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoMapper {

    public MercadoPagoDTO toMercadoPagoDTO(MercadoPagoResponse mercadoPagoResponse) {
        return new MercadoPagoDTO(mercadoPagoResponse);
    }
}
