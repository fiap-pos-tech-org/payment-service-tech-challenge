package br.com.fiap.techchallenge.servicocobranca.config;

import br.com.fiap.techchallenge.servicocobranca.core.ports.in.cobranca.*;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.cobranca.*;
import br.com.fiap.techchallenge.servicocobranca.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.servicocobranca.core.usecases.cobranca.*;
import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Bean
    BuscaCobrancaPorIdInputPort buscaCobrancaPorId(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        return new BuscaCobrancaPorIdUseCase(buscaCobrancaOutputPort);
    }

    @Bean
    BuscaCobrancaPorPedidoIdInputPort buscarCobrancaPorPedidoId(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        return new BuscaCobrancaPorPedidoIdUseCase(buscaCobrancaOutputPort);
    }

    @Bean
    CriaCobrancaInputPort criarCobranca(
            CriaCobrancaOutputPort criaCobrancaOutputPort,
            BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort,
            CriaCobrancaMercadoPagoOutputPort criaCobrancaMercadoPagoOutputPort
    ) {
        return new CriaCobrancaUseCase(
                criaCobrancaOutputPort,
                buscarPedidoPorIdOutputPort,
                buscaCobrancaOutputPort,
                criaCobrancaMercadoPagoOutputPort
        );
    }

    @Bean
    AtualizaStatusCobrancaInputPort atualizaStatusCobranca(
            AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        return new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort
        );
    }

    @Bean
    BuscaStatusPagamentoInputPort buscaStatusPagamento(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        return new BuscaStatusPagamentoUseCase(buscaStatusPagamentoOutputPort);
    }

    @Bean
    PublicaPagamentoRetornoInputPort publicaPagamentoRetornoInputPort(PublicaPagamentoRetornoOutputPort publicaPagamentoRetornoOutputPort) {
        return new PublicaPagamentoRetornoUseCase(publicaPagamentoRetornoOutputPort);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
