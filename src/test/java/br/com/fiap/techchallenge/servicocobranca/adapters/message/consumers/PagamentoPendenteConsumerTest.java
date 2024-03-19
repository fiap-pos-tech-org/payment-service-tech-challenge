package br.com.fiap.techchallenge.servicocobranca.adapters.message.consumers;

import br.com.fiap.techchallenge.servicocobranca.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PagamentoPendenteConsumerTest {

    @Autowired
    private ObjectMapper mapper;
    @Mock
    private CriaCobrancaInputPort criaCobrancaInputPort;
    private PagamentoPendenteConsumer pagamentoPendenteConsumer;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        pagamentoPendenteConsumer = new PagamentoPendenteConsumer(criaCobrancaInputPort, mapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve criar cobrança quando receber mensagem com o pagamento pendente")
    void deveCriarCobranca_QuandoReceberMensagemComPagamentoPendente() {
        //Arrange
        var msgString = "{\"idPedido\": 1, \"idCliente\": 2, \"valorTotal\": 122.3}";
        when(criaCobrancaInputPort.criar(any(CriaCobrancaDTO.class))).thenReturn(CobrancaHelper.criaCobrancaDTO());

        //Act
        pagamentoPendenteConsumer.receiveMessage(msgString);

        //Assert
        verify(criaCobrancaInputPort, times(1)).criar(any(CriaCobrancaDTO.class));
    }

    @Test
    @DisplayName("Deve lancarJsonProcessingException_QuandoNaoForPossivelSerializarMensagem")
    void deveLancarRuntimeException_QuandoNaoForPossivelSerializarMensagem() {
        //Arrange
        //Act
        assertThatThrownBy(() -> pagamentoPendenteConsumer.receiveMessage("{\\}"))
                .isInstanceOf(RuntimeException.class);

        //Assert
        verify(criaCobrancaInputPort, never()).criar(any(CriaCobrancaDTO.class));
    }

}
