package br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.MensagemDTOBase;

public interface PublicaPagamentoRetornoOutputPort {
    void publicar(MensagemDTOBase mensagem, String topicoArn);
}
