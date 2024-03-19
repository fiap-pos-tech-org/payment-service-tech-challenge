package br.com.fiap.techchallenge.servicocobranca.adapters.web.mappers;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import org.springframework.stereotype.Component;

@Component("CobrancaMapperWeb")
public class CobrancaMapper {
    public CobrancaResponse toCobrancaResponse(CobrancaDTO cobrancaOut) {
        return new CobrancaResponse(
                cobrancaOut.id(),
                cobrancaOut.pedidoId(),
                cobrancaOut.status(),
                cobrancaOut.valor(),
                cobrancaOut.qrCodeBase64()
        );
    }
}
