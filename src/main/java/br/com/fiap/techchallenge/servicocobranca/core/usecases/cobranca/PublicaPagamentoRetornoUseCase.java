package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.MensagemDTOBase;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.PublicaPagamentoRetornoInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.PublicaPagamentoRetornoOutputPort;

public class PublicaPagamentoRetornoUseCase implements PublicaPagamentoRetornoInputPort {

    PublicaPagamentoRetornoOutputPort publicaPagamentoRetornoOutputPort;

    public PublicaPagamentoRetornoUseCase(PublicaPagamentoRetornoOutputPort publicaPagamentoRetornoOutputPort) {
        this.publicaPagamentoRetornoOutputPort = publicaPagamentoRetornoOutputPort;
    }

    @Override
    public void publicar(MensagemDTOBase mensagem, String topicoArn) {
        publicaPagamentoRetornoOutputPort.publicar(mensagem, topicoArn);
    }

}
