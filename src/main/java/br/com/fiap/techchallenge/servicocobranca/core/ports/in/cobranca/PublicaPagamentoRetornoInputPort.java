package br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.MensagemDTOBase;

public interface PublicaPagamentoRetornoInputPort {
    void publicar(MensagemDTOBase mensagem, String topicoArn);
}
