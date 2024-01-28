package br.com.fiap.techchallenge.servicocobranca.adapters.gateways;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.PedidoMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.utils.PedidoHelper;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PedidoGatewayTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Mock
    private OkHttpClient okHttpClient;
    @Mock
    private Call call;
    @Mock
    private PedidoMapper pedidoMapper;
    @InjectMocks
    private PedidoGateway pedidoGateway;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        pedidoGateway = new PedidoGateway(okHttpClient, pedidoMapper, propertySourceResolver.getUrlApiPedidos());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve buscar o pedido por id")
    void deveBuscarPedido_QuandoInformadoUmId() throws Exception {
        var pedidoDTO = PedidoHelper.criaPedidoDTO();
        var pedidoResponse = PedidoHelper.criaPedidoResponse();
        var response = ResponseHelper.getResponse(pedidoResponse, 200);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(pedidoMapper.toPedidoDTO(any(PedidoResponse.class))).thenReturn(pedidoDTO);

        //Act
        var resultado = pedidoGateway.buscarPorId(1L);

        //Assert
        assertThat(resultado).isNotNull().satisfies(req -> {
            assertThat(req.id()).isEqualTo(1L);
            assertThat(req.getNomeCliente()).isEqualTo("cliente1");
            assertThat(req.itens()).hasSize(1);
            assertThat(req.status()).isEqualTo(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);
            assertThat(req.dataCriacao()).isNotNull();
        });

        verify(okHttpClient).newCall(any(Request.class));
    }

    @Test
    @DisplayName("Deve atualizar o status")
    void deveAtualizarStatus_QuandoInformadoUmId() throws Exception {
        var pedidoDTO = PedidoHelper.criaPedidoDTO();
        var pedidoResponse = PedidoHelper.criaPedidoResponse();
        var response = ResponseHelper.getResponse(pedidoResponse, 200);
        var outroResponse = ResponseHelper.getResponse(pedidoResponse, 200);

        //Arrange
        when(okHttpClient.newCall(any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response)
                .thenReturn(outroResponse);
        when(pedidoMapper.toPedidoDTO(any(PedidoResponse.class))).thenReturn(pedidoDTO);

        //Act
        var resultado = pedidoGateway.atualizarStatus(1L, StatusPedidoEnum.EM_PREPARACAO);

        //Assert
        assertThat(resultado).isNotNull().satisfies(req -> {
            assertThat(req.id()).isEqualTo(1L);
            assertThat(req.getNomeCliente()).isEqualTo("cliente1");
            assertThat(req.itens()).hasSize(1);
            assertThat(req.status()).isEqualTo(StatusPedidoEnum.PENDENTE_DE_PAGAMENTO);
            assertThat(req.dataCriacao()).isBefore(LocalDateTime.now());
        });

        verify(okHttpClient, times(2)).newCall(any(Request.class));
    }

}
