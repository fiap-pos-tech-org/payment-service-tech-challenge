package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;

import br.com.fiap.techchallenge.servicocobranca.utils.PedidoHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoMapperTest {

    @Test
    @DisplayName("Deve retornar um objeto tipo Cobranca quando receber como parâmetro um CobrancaDTO")
    void deveRetornarUmObjetoTipoCobranca_QuandoReceberComoParametroUmCobrancaDTO() {
        //Arrange
        var pedidoMapper = new PedidoMapper();
        //Act
        var pedidoDTO = pedidoMapper.toPedidoDTO(PedidoHelper.criaPedidoResponse());
        //Assert
        assertThat(pedidoDTO).isNotNull();
    }

}
