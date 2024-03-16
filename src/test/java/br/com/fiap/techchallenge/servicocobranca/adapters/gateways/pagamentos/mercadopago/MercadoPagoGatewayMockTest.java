package br.com.fiap.techchallenge.servicocobranca.adapters.gateways.pagamentos.mercadopago;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.MercadoPagoMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.MercadoPagoResponse;
import br.com.fiap.techchallenge.servicocobranca.utils.MercadoPagoHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.PropertySourceResolver;
import br.com.fiap.techchallenge.servicocobranca.utils.ResponseHelper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MercadoPagoGatewayMockTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Mock
    private OkHttpClient okHttpClient;
    @Mock
    private Call call;
    @Mock
    private MercadoPagoMapper mercadoPagoMapper;
    @InjectMocks
    private MercadoPagoGatewayMock mercadoPagoGatewayMock;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        mercadoPagoGatewayMock = new MercadoPagoGatewayMock(okHttpClient, mercadoPagoMapper, propertySourceResolver.getUrlMercadoPagoApiPagamentos());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve criar o pagamento no Mercado Pago")
    void deveCriarPagamento_QuandoInformadoUmId() throws Exception {
        var mercadoPagoDTO = MercadoPagoHelper.criaMercadoPagoDTO();
        var pedidoResponse = MercadoPagoHelper.criaMercadoPagoResponse();
        var response = ResponseHelper.getResponse(pedidoResponse, 200);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(mercadoPagoMapper.toMercadoPagoDTO(any(MercadoPagoResponse.class))).thenReturn(mercadoPagoDTO);

        //Act
        var resultado = mercadoPagoGatewayMock.criar(1L);

        //Assert
        assertThat(resultado).isNotNull().satisfies(req -> {
            assertThat(req.qrCodeBase64()).isEqualTo("iVBORw0KGgoAAAANSUhEUgAABRQAAAUUCAYAAACu5p7oAAAABGdBTUEAALGPC");
            assertThat(req.qrCode()).isEqualTo("00020126600014br");
        });

        verify(okHttpClient).newCall(any(Request.class));
    }

}
