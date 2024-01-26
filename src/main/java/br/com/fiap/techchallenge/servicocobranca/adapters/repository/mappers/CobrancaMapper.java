package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca;

import org.springframework.stereotype.Component;

@Component
public class CobrancaMapper {
    public Cobranca toCobranca(CobrancaDTO cobranca) {
        return new Cobranca(
                cobranca.pedidoId(),
                cobranca.status(),
                cobranca.valor(),
                cobranca.qrCode().getEncodedBase64Value()
        );
    }

    public CobrancaDTO toCobrancaOut(Cobranca cobranca) {
        return new CobrancaDTO(
            cobranca.getId(),
            cobranca.getPedidoId(),
            cobranca.getValor(),
            cobranca.getStatus(),
            new QrCode(cobranca.getQrCode())
        );
    }
}
