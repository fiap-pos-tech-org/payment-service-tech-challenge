package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import org.springframework.stereotype.Component;

@Component
public class CobrancaMapper {
    public Cobranca toCobranca(CobrancaDTO cobranca) {
        return new Cobranca(
                cobranca.pedidoId(),
                cobranca.status(),
                cobranca.valor(),
                cobranca.qrCodeBase64()
        );
    }

    public CobrancaDTO toCobrancaOut(Cobranca cobranca) {
        return new CobrancaDTO(
                cobranca.getId(),
                cobranca.getPedidoId(),
                cobranca.getValor(),
                cobranca.getStatus(),
                cobranca.getQrCode()
        );
    }
}
