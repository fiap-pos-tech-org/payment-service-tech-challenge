//package br.com.fiap.techchallenge.servicocobranca.adapters.repository.mappers;
//
//import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class CobrancaMapperTest {
//
//    @Test
//    @DisplayName("Deve retornar um objeto tipo Cobranca quando receber como parâmetro um CobrancaDTO")
//    void deveRetornarUmObjetoTipoCobranca_QuandoReceberComoParametroUmCobrancaDTO() {
//        //Arrange
//        var cobrancaMapper = new CobrancaMapper();
//        //Act
//        var cobranca = cobrancaMapper.toCobranca(CobrancaHelper.criaCobrancaDTO());
//        //Assert
//        assertThat(cobranca).isNotNull();
//    }
//
//    @Test
//    @DisplayName("Deve retornar um objeto tipo CobrancaDTO quando receber como parâmetro um Cobranca")
//    void deveRetornarUmObjetoTipoCobrancaDTO_QuandoReceberComoParametroUmCobranca() {
//        //Arrange
//        var cobrancaMapper = new CobrancaMapper();
//        //Act
//        var cobranca = cobrancaMapper.toCobrancaOut(CobrancaHelper.criaCobranca());
//        //Assert
//        assertThat(cobranca).isNotNull();
//    }
//
//}
