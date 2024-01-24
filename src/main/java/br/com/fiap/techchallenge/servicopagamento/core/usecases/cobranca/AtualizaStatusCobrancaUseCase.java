package br.com.fiap.techchallenge.servicopagamento.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicopagamento.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.dtos.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicopagamento.core.ports.in.cobranca.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicopagamento.core.ports.out.cobranca.BuscaCobrancaOutputPort;

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
        var novoStatusPedido = getStatusPedido(cobrancaStatusIn.status());
        if (novoStatusPedido != null) {
            atualizaStatusPedidoOutputPort.atualizarStatus(cobrancaOut.pedidoId(), novoStatusPedido);
        }
        return atualizaStatusCobrancaOutputPort.atualizarStatus(id, cobrancaStatusIn);
    }

    private StatusPedidoEnum getStatusPedido(StatusCobrancaEnum statusCobranca) {
        return switch(statusCobranca) {
            case PAGO -> StatusPedidoEnum.RECEBIDO;
            case CANCELADO, RECUSADO -> StatusPedidoEnum.CANCELADO;
            default -> null;
        };
    }
}
