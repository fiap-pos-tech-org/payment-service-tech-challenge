package br.com.fiap.techchallenge.servicocobranca.adapters.message.consumers;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.MensagemPedidoPagamentoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.CriaCobrancaInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PagamentoPendenteConsumer {

    private final Logger logger = LogManager.getLogger(PagamentoPendenteConsumer.class);

    private final CriaCobrancaInputPort criaCobrancaInputPort;
    private final ObjectMapper mapper;

    public PagamentoPendenteConsumer(CriaCobrancaInputPort criaCobrancaInputPort,
                                     ObjectMapper mapper) {
        this.criaCobrancaInputPort = criaCobrancaInputPort;
        this.mapper = mapper;
    }

    @Transactional
    @JmsListener(destination = "${aws.sqs.fila-pagamento-pendente}")
    public void receiveMessage(@Payload String mensagem) {
        logger.info("mensagem recebida do serviço pedido: {}", mensagem);
        MensagemPedidoPagamentoDTO mensagemDTO;
        try {
            mensagemDTO = mapper.readValue(mensagem, MensagemPedidoPagamentoDTO.class);
        } catch (JsonProcessingException e) {
            logger.error("erro ao serializar mensagem: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        criaCobrancaInputPort.criar(new CriaCobrancaDTO(mensagemDTO));
    }
}
