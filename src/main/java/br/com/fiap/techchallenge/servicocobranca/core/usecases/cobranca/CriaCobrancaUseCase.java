package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaCobrancaMercadoPagoOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;

public class CriaCobrancaUseCase implements CriaCobrancaInputPort {

    private final CriaCobrancaOutputPort cobrancaOutputPort;
    private final BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;
    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;
    private final CriaCobrancaMercadoPagoOutputPort criaCobrancaMercadoPagoOutputPort;

    public CriaCobrancaUseCase(CriaCobrancaOutputPort cobrancaOutputPort,
                               BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
                               BuscaCobrancaOutputPort buscaCobrancaOutputPort,
                               CriaCobrancaMercadoPagoOutputPort criaCobrancaMercadoPagoOutputPort) {
        this.cobrancaOutputPort = cobrancaOutputPort;
        this.buscarPedidoPorIdOutputPort = buscarPedidoPorIdOutputPort;
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
        this.criaCobrancaMercadoPagoOutputPort = criaCobrancaMercadoPagoOutputPort;
    }

    public CobrancaDTO criar(CriaCobrancaDTO cobrancaIn) {
        var pedidoOut = buscarPedidoPorIdOutputPort.buscarPorId(cobrancaIn.pedidoId());

        validaExisteCobranca(pedidoOut.id());

        var mercadoPagoDTO = criaCobrancaMercadoPagoOutputPort.criar(cobrancaIn.pedidoId());
        var cobranca = new Cobranca(
                cobrancaIn.pedidoId(), StatusCobrancaEnum.PENDENTE, pedidoOut.valorTotal(), mercadoPagoDTO.qrCodeBase64()
        );
        return cobrancaOutputPort.criar(new CobrancaDTO(cobranca));
    }

    private void validaExisteCobranca(Long pedidoId) {
        if (buscaCobrancaOutputPort.pedidoPossuiCobranca(pedidoId)) {
            throw new EntityAlreadyExistException("Já existe uma cobrança para o pedido " + pedidoId);
        }
    }
}
