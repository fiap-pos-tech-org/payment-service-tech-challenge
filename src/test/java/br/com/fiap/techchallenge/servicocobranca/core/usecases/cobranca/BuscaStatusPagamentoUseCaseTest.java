package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.adapters.gateways.pagamentos.mercadopago.StatusPagamento;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.NotFoundException;
import br.com.fiap.techchallenge.servicocobranca.utils.ErrorDetailsHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.PropertySourceResolver;
import br.com.fiap.techchallenge.servicocobranca.utils.ResponseHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.StatusPedidoMercadoPagoHelper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BuscaStatusPagamentoUseCaseTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Mock
    private OkHttpClient okHttpClient;
    @Mock
    private Call call;
    @InjectMocks
    private StatusPagamento statusPagamento;
    private BuscaStatusPagamentoUseCase buscaStatusPagamentoUseCase;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        statusPagamento = new StatusPagamento(okHttpClient, propertySourceResolver.getUrlMercadoPagoApiPagamentos(),
                propertySourceResolver.getToken());
        buscaStatusPagamentoUseCase = new BuscaStatusPagamentoUseCase(statusPagamento);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar o status do pagamento pelo id")
    void deveBuscarStatusPagamento_QuandoInformadoUmId() throws Exception {
        var statusPedidoMercadoPago = StatusPedidoMercadoPagoHelper.criaStatusPedidoMercadoPago();
        var response = ResponseHelper.getResponse(statusPedidoMercadoPago, 200);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);

        //Act
        var statusPagamentoDTO = buscaStatusPagamentoUseCase.buscaStatus(1L);

        //Assert
        assertThat(statusPagamentoDTO).isNotNull().satisfies(req -> {
            assertThat(req.id()).isEqualTo(1L);
            assertThat(req.statusPedido()).isEqualTo("approved");
            assertThat(req.statusPagamento()).isEqualTo(StatusCobrancaEnum.PAGO);
        });

        verify(okHttpClient).newCall(any(Request.class));
    }

    @Test
    @DisplayName("Deve lancar NotFoundException quando é retornado erro na busca do status")
    void deveLancarNotFoundException_QuandoHaErroBuscaStatus() throws Exception {
        final var httpStatus = 404;
        var errorDetails = ErrorDetailsHelper.criarErrorDetails(httpStatus);
        var response = ResponseHelper.getResponse(errorDetails, httpStatus);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);

        //Act
        var throwable = catchThrowable(() -> buscaStatusPagamentoUseCase.buscaStatus(1L));

        //Assert
        assertThat(throwable)
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("alguma exception");

        verify(okHttpClient).newCall(any(Request.class));
    }

}
