package br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca;

import br.com.fiap.techchallenge.servicocobranca.adapters.repository.CobrancaRepository;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers.CobrancaMapper;
import br.com.fiap.techchallenge.servicocobranca.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.servicocobranca.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.CriaCobrancaDTO;
import br.com.fiap.techchallenge.servicocobranca.core.dtos.PedidoDTO;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.CriaQrCodeOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CriaCobrancaUseCaseTest {

    @Mock
    private CobrancaJpaRepository cobrancaJpaRepository;
    @Mock
    private CobrancaMapper cobrancaMapper;
    @InjectMocks
    private CobrancaRepository cobrancaRepository;
    private CriaCobrancaUseCase cobrancaUseCase;
    @Mock
    private CriaQrCodeOutputPort criaQrCodeOutputPort;
    @Mock
    private BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;
    @Mock
    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;
    private Cobranca cobrancaSalva;
    private CobrancaDTO cobrancaDto;
    private PedidoDTO pedidoDTO;
    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);

        cobrancaUseCase = new CriaCobrancaUseCase(
                cobrancaRepository,
                criaQrCodeOutputPort,
                buscarPedidoPorIdOutputPort,
                buscaCobrancaOutputPort
        );

        cobrancaSalva = CobrancaHelper.criaCobranca();
        cobrancaDto = CobrancaHelper.criaCobrancaDTO();
        pedidoDTO = PedidoHelper.criaPedidoDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Deve criar uma cobrança quando todos os atributos são corretamente informados")
    void deveCriarUmaCobranca_QuandoTodosOsAtributosSaoInformadosCorretamente() {
        //Arrange
        CriaCobrancaDTO novaCobrancaDTO = new CriaCobrancaDTO(1L);
        when(buscarPedidoPorIdOutputPort.buscarPorId(anyLong())).thenReturn(pedidoDTO);
        when(buscaCobrancaOutputPort.pedidoPossuiCobranca(anyLong())).thenReturn(false);
        when(criaQrCodeOutputPort.criar(anyLong(), any(BigDecimal.class))).thenReturn(new QrCode("1234"));
        when(cobrancaMapper.toCobranca(any(CobrancaDTO.class))).thenReturn(cobrancaSalva);
        when(cobrancaJpaRepository.save(any(Cobranca.class))).thenReturn(cobrancaSalva);
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDto);

        //Act
        CobrancaDTO novaCobranca = cobrancaUseCase.criar(novaCobrancaDTO);

        //Assert
        assertThat(novaCobranca).isNotNull();
        verify(buscarPedidoPorIdOutputPort, times(1)).buscarPorId(anyLong());
        verify(buscaCobrancaOutputPort, times(1)).pedidoPossuiCobranca(anyLong());
        verify(criaQrCodeOutputPort, times(1)).criar(anyLong(), any(BigDecimal.class));
        verify(cobrancaMapper, times(1)).toCobranca(any(CobrancaDTO.class));
        verify(cobrancaJpaRepository, times(1)).save(any(Cobranca.class));
        verify(cobrancaMapper, times(1)).toCobrancaOut(any(Cobranca.class));
    }

    @Test
    @DisplayName("Deve lançar EntityAlreadyExistException quando já existir uma cobrança associada ao id do pedido informado")
    void deveLancarEntityAlreadyExistException_QuandoJaExistirUmaCobrancaAssiciadaAoIdDoPedidoInformado() {
        //Arrange
        CriaCobrancaDTO novaCobrancaDTO = new CriaCobrancaDTO(1L);
        when(buscarPedidoPorIdOutputPort.buscarPorId(anyLong())).thenReturn(pedidoDTO);
        when(buscaCobrancaOutputPort.pedidoPossuiCobranca(anyLong())).thenReturn(true);

        //Act
        //Assert
        assertThatThrownBy(() -> cobrancaUseCase.criar(novaCobrancaDTO))
                .isInstanceOf(EntityAlreadyExistException.class)
                .hasMessage("Já existe uma cobrança para o pedido " + novaCobrancaDTO.pedidoId());

        verify(buscarPedidoPorIdOutputPort, times(1)).buscarPorId(anyLong());
    }
}