package br.com.fiap.techchallenge.servicocobranca.adapters.web;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.WebhookStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.StatusPagamentoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaStatusPagamentoInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Tag(name = "Cobranca", description = "APIs para geração e confirmação de pagamento de cobranças")
@Validated
@RestController
@RequestMapping("/cobrancas")
public class CobrancaController extends ControllerBase {

    private final Logger logger = LogManager.getLogger(CobrancaController.class);
    private final BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;
    private final AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;
    private final CobrancaMapper cobrancaMapper;
    private final BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;
    private final BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort;

    public CobrancaController(
            BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort,
            AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort,
            CobrancaMapper cobrancaMapper,
            BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort,
            BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort
    ) {
        this.buscaCobrancaPorIdInputPort = buscaCobrancaPorIdInputPort;
        this.atualizaStatusCobrancaInputPort = atualizaStatusCobrancaInputPort;
        this.cobrancaMapper = cobrancaMapper;
        this.buscaStatusPagamentoInputPort = buscaStatusPagamentoInputPort;
        this.buscaCobrancaPorPedidoIdInputPort = buscaCobrancaPorPedidoIdInputPort;
    }

    @Operation(summary = "Busca uma Cobrança por id")
    @GetMapping(value = "/{id}")
    ResponseEntity<CobrancaResponse> get(@Parameter(example = "1")
                                         @PathVariable("id")
                                         @Pattern(regexp = "^\\d*$", message = "O id da cobrança deve conter apenas números") String id) {
        var cobrancaOut = buscaCobrancaPorIdInputPort.buscarPorId(Long.parseLong(id));
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(summary = "Busca cobrança pelo id do pedido")
    @GetMapping(value = "/{id}/pedido")
    ResponseEntity<CobrancaResponse> buscarCobrancaPorPedidoId(@Parameter(example = "1")
                                                               @PathVariable("id")
                                                               @Pattern(regexp = "^\\d*$", message = "O id do pedido deve conter apenas números") String id) {
        var cobrancaOut = buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(Long.parseLong(id));
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(summary = "Atualiza o status de uma cobrança para Pago ou Cancelado")
    @PutMapping(value = "/{id}/status")
    ResponseEntity<CobrancaResponse> updateStatus(
            @Parameter(example = "1")
            @PathVariable("id")
            @Pattern(regexp = "^\\d*$", message = "O id da cobrança deve conter apenas números") String id,
            @Valid @RequestBody AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest
    ) {
        var cobrancaOut = atualizaStatusCobrancaInputPort.atualizarStatus(Long.parseLong(id), atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(
            summary = "Atualiza o status de uma cobrança para Pago ou Recusado, baseado na requisição pelo serviço de webhook do MercadoPago"
    )
    @PutMapping(value = "/{id}/webhook-status")
    ResponseEntity<Object> updateWebhookStatus(
            @Parameter(example = "1")
            @PathVariable("id")
            @Pattern(regexp = "^\\d*$", message = "O id da cobrança deve conter apenas números") String id,
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

            atualizaStatusCobrancaInputPort.atualizarStatus(Long.parseLong(id), atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
            return response;
        } catch (Exception ex) {
            logger.error(Arrays.toString(ex.getStackTrace()));
            return response;
        }
    }
}
