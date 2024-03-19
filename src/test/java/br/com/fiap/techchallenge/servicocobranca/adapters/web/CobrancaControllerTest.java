package br.com.fiap.techchallenge.servicocobranca.adapters.web;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.handlers.ExceptionsHandler;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.requests.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.ObjectParaJsonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CobrancaControllerTest {

    private MockMvc mockMvc;
    @Mock
    private BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;
    @Mock
    private AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;
    @Mock
    private CobrancaMapper cobrancaMapper;
    @Mock
    private BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;
    @Mock
    private BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort;
    private AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        var cobrancaController = new CobrancaController(
                buscaCobrancaPorIdInputPort,
                atualizaStatusCobrancaInputPort,
                cobrancaMapper,
                buscaStatusPagamentoInputPort,
                buscaCobrancaPorPedidoIdInputPort
        );

        mockMvc = MockMvcBuilders.standaloneSetup(cobrancaController)
                .setControllerAdvice(new ExceptionsHandler())
                .build();

        atualizaStatusCobrancaRequest = new AtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    @DisplayName("Busca uma cobranca")
    class BuscaCobranca {
        @Test
        @DisplayName("Deve retornar uma cobrança quando o id for informado")
        void deveRetornarUmaCobranca_QuandoIdForInformado() throws Exception {
            //Arrange
            when(buscaCobrancaPorIdInputPort.buscarPorId(any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(get("/cobrancas/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("PENDENTE"));
        }

        @Test
        @DisplayName("Deve retornar NotFound quando o id do pedido não for encontrado")
        void deveRetornarNotFound_QuandoIdPedidoNaoEncontrado() throws Exception {
            //Arrange
            when(buscaCobrancaPorIdInputPort.buscarPorId(any())).thenThrow(EntityNotFoundException.class);

            //Act
            //Assert
            mockMvc.perform(get("/cobrancas/{id}", 1))
                    .andExpect(status().isNotFound());

            verify(buscaCobrancaPorIdInputPort, times(1)).buscarPorId(anyLong());
        }

        @Test
        @DisplayName("Deve retornar uma cobrança por id do pedido")
        void deveRetornarCobrancaPorIdPedido() throws Exception {
            //Arrange
            when(buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(anyLong())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any(CobrancaDTO.class))).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(get("/cobrancas/{id}/pedido", 1))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").value(1),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.pedidoId").value(1),
                            jsonPath("$.pedidoId").isNumber(),
                            jsonPath("$.status").value("PENDENTE"),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valor").value(1),
                            jsonPath("$.valor").isNumber(),
                            jsonPath("$.qrCode").value("1234"),
                            jsonPath("$.qrCode").isString()
                    );
        }
    }

    @Nested
    @DisplayName("Atualiza o status de uma cobranca")
    class AtualizaStatusCobranca {
        @Test
        @DisplayName("Deve atualizar o status de uma cobrança quando o id informado existir e os dados forem informados corretamente")
        void deveAtualizarStatusCobranca_QuandoIdInformadoExistirEDadosCorretos() throws Exception {
            //Arrange
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(put("/cobrancas/{id}/status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(new AtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO))))
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.id").isNotEmpty(),
                            jsonPath("$.id").isNumber(),
                            jsonPath("$.pedidoId").isNotEmpty(),
                            jsonPath("$.pedidoId").isNumber(),
                            jsonPath("$.status").isNotEmpty(),
                            jsonPath("$.status").isString(),
                            jsonPath("$.valor").isNotEmpty(),
                            jsonPath("$.valor").isNumber(),
                            jsonPath("$.qrCode").isNotEmpty(),
                            jsonPath("$.qrCode").isString()
                    );

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
            verify(cobrancaMapper, times(1)).toCobrancaResponse(any());
        }

        @Test
        @DisplayName("Deve retornar NotFound quando o id do pedido não for encontrado")
        void deveRetornarNotFound_QuandoIdPedidoNaoEncontrado() throws Exception {
            //Arrange
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenThrow(EntityNotFoundException.class);

            //Act
            //Assert
            mockMvc.perform(put("/cobrancas/{id}/status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(atualizaStatusCobrancaRequest)))
                    .andExpect(status().isNotFound());

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
        }
    }

    @Nested
    @DisplayName("Atualiza o status de uma cobranca via webhook")
    class AtualizaStatusCobrancaViaWebhook {
        @Test
        @DisplayName("Deve atualizar o status de uma cobrança quando através do webhook do MercadoPago")
        void deveAtualizarStatusCobranca_QuandoIdInformadoExistirEDadosCorretos() throws Exception {
            //Arrange
            when(buscaStatusPagamentoInputPort.buscaStatus(anyLong())).thenReturn(CobrancaHelper.criaStatusPagamentoDTO());
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(put("/cobrancas/{id}/webhook-status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(CobrancaHelper.criaWebhookStatusCobrancaRequest())))
                    .andExpect(status().isNoContent());

            verify(buscaStatusPagamentoInputPort, times(1)).buscaStatus(anyLong());
            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(anyLong(), any());
        }

        @Test
        @DisplayName("Não deve atualizar o status de uma cobrança através do webhook do MercadoPago quando parâmetro actions é igual created")
        void naoDeveAtualizarStatusCobranca_QuandoParametroActionEhIgualCreated() throws Exception {
            //Arrange
            when(buscaStatusPagamentoInputPort.buscaStatus(anyLong())).thenReturn(CobrancaHelper.criaStatusPagamentoDTO());
            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), any())).thenReturn(CobrancaHelper.criaCobrancaDTO());
            when(cobrancaMapper.toCobrancaResponse(any())).thenReturn(CobrancaHelper.criaCobrancaResponse());

            //Act
            //Assert
            mockMvc.perform(put("/cobrancas/{id}/webhook-status", 1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(ObjectParaJsonMapper.converte(CobrancaHelper.criaWebhookStatusCobrancaRequest("created"))))
                    .andExpect(status().isNoContent());

            verify(buscaStatusPagamentoInputPort, never()).buscaStatus(anyLong());
            verify(atualizaStatusCobrancaInputPort, never()).atualizarStatus(anyLong(), any());
        }

    }

}
