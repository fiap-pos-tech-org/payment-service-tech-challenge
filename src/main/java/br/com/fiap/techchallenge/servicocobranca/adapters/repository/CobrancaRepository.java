package br.com.fiap.techchallenge.servicocobranca.adapters.repository;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.*;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.PublicaPagamentoRetornoInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CobrancaRepository implements CriaCobrancaOutputPort, BuscaCobrancaOutputPort, AtualizaStatusCobrancaOutputPort {

    @Value("${aws.sns.topico-pagamento-retorno-arn}")
    private String topicoPagamentoRetornoArn;

    private final CobrancaJpaRepository cobrancaJpaRepository;
    private final CobrancaMapper cobrancaMapper;
    private final PublicaPagamentoRetornoInputPort publicaPagamentoRetornoInputPort;

    public CobrancaRepository(CobrancaJpaRepository cobrancaJpaRepository, CobrancaMapper cobrancaMapper,
                              PublicaPagamentoRetornoInputPort publicaPagamentoRetornoInputPort) {
        this.cobrancaJpaRepository = cobrancaJpaRepository;
        this.cobrancaMapper = cobrancaMapper;
        this.publicaPagamentoRetornoInputPort = publicaPagamentoRetornoInputPort;
    }
    @Override
    public CobrancaDTO criar(CobrancaDTO cobrancaDTO) {
        var cobranca = cobrancaMapper.toCobranca(cobrancaDTO);
        var cobrancaSalva = cobrancaJpaRepository.save(cobranca);
        return cobrancaMapper.toCobrancaOut(cobrancaSalva);
    }

    @Override
    public CobrancaDTO buscarPorId(Long id) {
        var cobranca = buscaCobrancaPorId(id);
        return cobrancaMapper.toCobrancaOut(cobranca);
    }

    @Override
    public CobrancaDTO buscarPorPedidoId(Long pedidoId) {
        var cobranca = cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(pedidoId)
                                            .orElseThrow(() -> new EntityNotFoundException("Cobrança com o pedidoId " + pedidoId + " não existe"));
        return cobrancaMapper.toCobrancaOut(cobranca);
    }

    @Override
    public boolean pedidoPossuiCobranca(Long pedidoId) {
        return cobrancaJpaRepository.existsCobrancaByPedidoId(pedidoId);
    }


    @Override
    public CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn) {
        var cobranca = buscaCobrancaPorId(id);
        cobranca.setStatus(cobrancaStatusIn.status());
        var cobrancaSalva = cobrancaJpaRepository.save(cobranca);

        var statusPedidoEnum = StatusPedidoEnum.getStatusPedido(cobrancaStatusIn.status());
        var mensagem = new MensagemPedidoProducaoDTO(
                cobrancaSalva.getPedidoId(),
                statusPedidoEnum,
                new ArrayList<>()
        );
        publicaPagamentoRetornoInputPort.publicar(mensagem, topicoPagamentoRetornoArn);

        return cobrancaMapper.toCobrancaOut(cobrancaSalva);
    }

    private br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca buscaCobrancaPorId(Long id) {
        return cobrancaJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cobrança com o id " + id + " não existe"));
    }
}
