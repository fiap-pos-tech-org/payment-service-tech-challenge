package br.com.fiap.techchallenge.servicocobranca.adapters.web;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.CobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Tag(name = "Cobranca", description = "APIs para geração e confirmação de pagamento de cobranças")
@RestController
@RequestMapping("/cobrancas")
public class CobrancaController extends ControllerBase {

    private final Logger logger = LoggerFactory.getLogger(CobrancaController.class);
    private final CriaCobrancaInputPort criaCobrancaInputPort;
    private final BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;
    private final AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;
    private final CobrancaMapper cobrancaMapper;
    private final BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;
    private final BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort;

    public CobrancaController(
            CriaCobrancaInputPort criaCobrancaInputPort,
            BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort,
            AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort,
            CobrancaMapper cobrancaMapper,
            BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort,
            BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort
    ) {
        this.criaCobrancaInputPort = criaCobrancaInputPort;
        this.buscaCobrancaPorIdInputPort = buscaCobrancaPorIdInputPort;
        this.atualizaStatusCobrancaInputPort = atualizaStatusCobrancaInputPort;
        this.cobrancaMapper = cobrancaMapper;
        this.buscaStatusPagamentoInputPort = buscaStatusPagamentoInputPort;
        this.buscaCobrancaPorPedidoIdInputPort = buscaCobrancaPorPedidoIdInputPort;
    }

    @Operation(summary = "Cria uma nova Cobrança")
    @PostMapping
    ResponseEntity<CobrancaResponse> criar(@Valid @RequestBody CobrancaRequest cobrancaRequest) {
        var cobrancaOut = criaCobrancaInputPort.criar(cobrancaRequest.toCriaCobrancaDTO());
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        var uri = getExpandedCurrentUri("/{id}", cobrancaResponse.getId());

        return ResponseEntity.created(uri).body(cobrancaResponse);
    }

    @Operation(summary = "Busca uma Cobrança por id")
    @GetMapping(value = "/{id}")
    ResponseEntity<CobrancaResponse> get(@Parameter(example = "1") @PathVariable("id") Long id) {
        var cobrancaOut = buscaCobrancaPorIdInputPort.buscarPorId(id);
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(summary = "Busca cobrança pelo id do pedido")
    @GetMapping(value = "/{id}/pedido")
    ResponseEntity<CobrancaResponse> buscarCobrancaPorPedidoId(@Parameter(example = "1") @PathVariable("id") Long id) {
        var cobrancaOut = buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(id);
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(summary = "Atualiza o status de uma cobrança para Pago ou Cancelado")
    @PutMapping(value = "/{id}/status")
    ResponseEntity<CobrancaResponse> updateStatus(
            @Parameter(example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest
    ) {
        var cobrancaOut = atualizaStatusCobrancaInputPort.atualizarStatus(id, atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(
            summary = "Atualiza o status de uma cobrança para Pago ou Recusado, baseado na requisição pelo serviço de webhook do MercadoPago"
    )
    @PutMapping(value = "/{id}/webhook-status")
    ResponseEntity<Object> updateWebhookStatus(
            @Parameter(example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody WebhookStatusCobrancaRequest request
    ) {
        ResponseEntity<Object> response = ResponseEntity.noContent().build();

        if (request.getAction().contains("created")) {
            logger.info("Pagamento com id: {} criado", id);
            return response;
        }

        try {
            StatusPagamentoDTO statusPedidoPagamento = buscaStatusPagamentoInputPort.buscaStatus(request.getData().getId());
            AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest =
                    new AtualizaStatusCobrancaRequest(statusPedidoPagamento.statusPagamento());

            atualizaStatusCobrancaInputPort.atualizarStatus(id, atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
            return response;
        } catch (Exception ex) {
            logger.error(Arrays.toString(ex.getStackTrace()));
            return response;
        }
    }
}
