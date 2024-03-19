package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.CobrancaRepository;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.BadRequestException;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.PublicaPagamentoRetornoInputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AtualizaStatusCobrancaUseCaseTest {

    @Mock
    private CobrancaJpaRepository cobrancaJpaRepository;
    @Mock
    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;
    @Mock
    private AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort;
    @Mock
    private PublicaPagamentoRetornoInputPort publicaPagamentoRetornoInputPort;
    private CobrancaMapper cobrancaMapper = new CobrancaMapper();
    private CobrancaRepository cobrancaRepository;
    private AtualizaStatusCobrancaUseCase cobrancaUseCase;
    private CobrancaDTO cobrancaDTO;
    private Cobranca cobrancaSalva;
    private AtualizaStatusCobrancaDTO atualizaStatusCobranca;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        cobrancaRepository = new CobrancaRepository(cobrancaJpaRepository, cobrancaMapper, publicaPagamentoRetornoInputPort);
        cobrancaUseCase = new AtualizaStatusCobrancaUseCase(
                cobrancaRepository,
                cobrancaRepository,
                atualizaStatusPedidoOutputPort
        );

        cobrancaSalva = CobrancaHelper.criaCobranca();
        cobrancaDTO = CobrancaHelper.criaCobrancaDTO();
        atualizaStatusCobranca = new AtualizaStatusCobrancaDTO(StatusCobrancaEnum.RECUSADO);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve atualizar status da cobrança quando dados informados corretamente")
    void deveAtualizarStatusCobranca_QuandoDadosInformadosCorretamente() {
        //Arrange
        Long id = 1L;
        PedidoDTO pedidoDTO = PedidoHelper.criaPedidoDTO();

        when(atualizaStatusPedidoOutputPort.atualizarStatus(cobrancaDTO.id(), StatusPedidoEnum.CANCELADO)).thenReturn(pedidoDTO);
        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.of(cobrancaSalva));
        when(cobrancaJpaRepository.save(any(Cobranca.class))).thenReturn(cobrancaSalva);

        //Act
        CobrancaDTO cobrancaDtoAtualizada = cobrancaUseCase.atualizarStatus(id, atualizaStatusCobranca);

        //Assert
        assertThat(cobrancaDtoAtualizada).isNotNull();
        assertThat(cobrancaDtoAtualizada.status()).isEqualTo(StatusCobrancaEnum.RECUSADO);
        verify(atualizaStatusPedidoOutputPort, times(1)).atualizarStatus(anyLong(), any(StatusPedidoEnum.class));
        verify(cobrancaJpaRepository, times(2)).findById(anyLong());
        verify(cobrancaJpaRepository, times(1)).save(any(Cobranca.class));
    }

    @Test
    @DisplayName("Deve atualizar status da cobrança quando dados informados corretamente2")
    void deveAtualizarStatusCobranca_QuandoDadosInformadosCorretamente2() {
        //Arrange
        Long id = 1L;
        PedidoDTO pedidoDTO = PedidoHelper.criaPedidoDTO();
        var atualizaStatusCobranca = new AtualizaStatusCobrancaDTO(StatusCobrancaEnum.PAGO);

        when(atualizaStatusPedidoOutputPort.atualizarStatus(cobrancaDTO.id(), StatusPedidoEnum.RECEBIDO)).thenReturn(pedidoDTO);
        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.of(cobrancaSalva));
        when(cobrancaJpaRepository.save(any(Cobranca.class))).thenReturn(cobrancaSalva);

        //Act
        CobrancaDTO cobrancaDtoAtualizada = cobrancaUseCase.atualizarStatus(id, atualizaStatusCobranca);

        //Assert
        assertThat(cobrancaDtoAtualizada).isNotNull();
        assertThat(cobrancaDtoAtualizada.status()).isEqualTo(StatusCobrancaEnum.PAGO);
        verify(atualizaStatusPedidoOutputPort, times(1)).atualizarStatus(anyLong(), any(StatusPedidoEnum.class));
        verify(cobrancaJpaRepository, times(2)).findById(anyLong());
        verify(cobrancaJpaRepository, times(1)).save(any(Cobranca.class));
    }

    @Test
    @DisplayName("Deve retornar BadRequestException quando status cobranca for diferente de pendente")
    void deveRetornarBadRequestException_QuandoStatusCobrancaForDiferenteDePendente() {
        //Arrange
        Long id = 1L;
        String message = String.format("Cobranca %s não pode mais ser atualizada.", id);
        Cobranca cobrancaRecusada = new Cobranca(1L, 1L, StatusCobrancaEnum.RECUSADO, BigDecimal.valueOf(1),
                "1234", LocalDateTime.now(), LocalDateTime.now());

        when(cobrancaJpaRepository.findById(id)).thenReturn(Optional.of(cobrancaRecusada));

        //Act
        //Assert
        assertThatThrownBy(() -> cobrancaUseCase.atualizarStatus(id, atualizaStatusCobranca))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(message);

        verify(cobrancaJpaRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Deve retornar EntityNotFoundException quando id informado não existir")
    void deveRetornarEntityNotFoundException_QuandoIdInformadoNaoExiste() {
        //Arrange
        Long id = 1L;
        String message = String.format("Cobrança com o id %s não existe", id);
        when(cobrancaJpaRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        //Assert
        assertThatThrownBy(() -> cobrancaUseCase.atualizarStatus(id, atualizaStatusCobranca))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);

        verify(cobrancaJpaRepository, times(1)).findById(anyLong());
    }

}
