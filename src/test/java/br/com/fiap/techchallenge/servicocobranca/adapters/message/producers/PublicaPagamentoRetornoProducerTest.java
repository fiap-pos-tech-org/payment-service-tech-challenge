package br.com.fiap.techchallenge.servicocobranca.adapters.message.producers;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.MensagemPedidoPagamentoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PublicaPagamentoRetornoProducerTest {

    @Mock
    private ObjectMapper mapper;
    @Mock
    private SnsClient snsClient;
    @InjectMocks
    private PublicaPagamentoRetornoProducer publicaPagamentoRetornoProducer;
    private MensagemPedidoPagamentoDTO mensagemPedidoPagamento;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        mensagemPedidoPagamento = new MensagemPedidoPagamentoDTO(1L, 2L, BigDecimal.valueOf(59.9));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve publicar mensagem no tópico retorno quando objeto mensagem e arn do tópico forem informados")
    void devePublicarMensagemTopicoRetorno_QuandoObjetoMensagemEArnTopicoInformados() throws JsonProcessingException {
        //Arrange
        PublishResponse response = PublishResponse.builder().messageId(UUID.randomUUID().toString()).build();
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(response);
        when(mapper.writeValueAsString(any())).thenReturn(new ObjectMapper().writeValueAsString(mensagemPedidoPagamento));

        //Act
        publicaPagamentoRetornoProducer.publicar(mensagemPedidoPagamento, "arn::0001");

        //Assert
        verify(snsClient, times(1)).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando objeto mensagem não for serializado")
    void deveLancarRuntimeException_QuandoObjetoMensagemNaoForSerializado() throws JsonProcessingException {
        //Arrange
        when(mapper.writeValueAsString(any(MensagemPedidoPagamentoDTO.class))).thenThrow(JsonProcessingException.class);

        //Act
        //Assert
        assertThatThrownBy(() -> publicaPagamentoRetornoProducer.publicar(mensagemPedidoPagamento, "arn::0001"))
                .isInstanceOf(RuntimeException.class);

        verify(snsClient, never()).publish(any(PublishRequest.class));
    }

}