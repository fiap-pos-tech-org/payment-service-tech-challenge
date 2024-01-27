package br.com.fiap.techchallenge.servicocobranca.adapters.web.mappers;

import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CobrancaMapperTest {

    @Test
    @DisplayName("Deve retornar um objeto tipo CobrancaResponse quando receber como parâmetro um CobrancaDTO")
    void deveRetornarUmObjetoTipoCobrancaResponse_QuandoReceberComoParametroUmCobrancaDTO() {
        //Arrange
        CobrancaMapper cobrancaMapper = new CobrancaMapper();
        //Act
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(CobrancaHelper.criaCobrancaDTO());
        //Assert
        assertThat(cobrancaResponse).isNotNull();
    }

}
