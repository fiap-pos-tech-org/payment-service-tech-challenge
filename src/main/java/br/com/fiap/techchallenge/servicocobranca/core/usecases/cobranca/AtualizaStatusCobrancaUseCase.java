package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;

public class AtualizaStatusCobrancaUseCase implements AtualizaStatusCobrancaInputPort {

    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    private final AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort;

    private final AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort;

    public AtualizaStatusCobrancaUseCase(
        BuscaCobrancaOutputPort buscaCobrancaOutputPort,
        AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort,
        AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort
    ) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
        this.atualizaStatusCobrancaOutputPort = atualizaStatusCobrancaOutputPort;
        this.atualizaStatusPedidoOutputPort = atualizaStatusPedidoOutputPort;
    }
    @Override
    public CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn) {
        var cobrancaOut = buscaCobrancaOutputPort.buscarPorId(id);
        if (cobrancaOut.status() != StatusCobrancaEnum.PENDENTE) {
            throw new BadRequestException("Cobranca "+id+" não pode mais ser atualizada.");
        }
//        var novoStatusPedido = StatusPedidoEnum.getStatusPedido(cobrancaStatusIn.status());
//        if (novoStatusPedido != null) {
//            atualizaStatusPedidoOutputPort.atualizarStatus(cobrancaOut.pedidoId(), novoStatusPedido);
//        }
        return atualizaStatusCobrancaOutputPort.atualizarStatus(id, cobrancaStatusIn);
    }
}
