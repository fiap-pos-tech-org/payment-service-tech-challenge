package br.com.fiap.techchallenge.servicopagamento.adapters.web.mappers;

import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import org.springframework.stereotype.Component;

@Component("CobrancaMapperWeb")
public class CobrancaMapper {
    public CobrancaResponse toCobrancaResponse(CobrancaDTO cobrancaOut) {
        return new CobrancaResponse(
                cobrancaOut.id(),
                cobrancaOut.pedidoId(),
                cobrancaOut.status(),
                cobrancaOut.valor(),
                cobrancaOut.qrCode()
        );
    }
}
