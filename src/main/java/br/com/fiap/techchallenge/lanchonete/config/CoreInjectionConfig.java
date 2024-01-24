package br.com.fiap.techchallenge.lanchonete.config;

import br.com.fiap.techchallenge.lanchonete.adapters.gateways.pagamentos.mercadopago.PagamentoMock;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cliente.AtualizaClienteInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cliente.BuscaClientePorInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cliente.BuscaTodosClientesInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cliente.CadastraClienteInputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.in.cobranca.*;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cliente.AtualizaClienteOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cliente.BuscaClienteOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cliente.BuscaTodosClientesOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cliente.CadastraClienteOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.cobranca.*;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.pedido.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.ports.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.lanchonete.core.usecases.cliente.AtualizaClienteUseCase;
import br.com.fiap.techchallenge.lanchonete.core.usecases.cliente.BuscaClientePorUseCase;
import br.com.fiap.techchallenge.lanchonete.core.usecases.cliente.BuscaTodosClientesUseCase;
import br.com.fiap.techchallenge.lanchonete.core.usecases.cliente.CadastraClienteUseCase;
import br.com.fiap.techchallenge.lanchonete.core.usecases.cobranca.*;
import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Bean
    AtualizaClienteInputPort atualizaCliente(AtualizaClienteOutputPort atualizaClienteOutputPort) {
        return new AtualizaClienteUseCase(atualizaClienteOutputPort);
    }

    @Bean
    BuscaClientePorInputPort buscaClientePorCpf(BuscaClienteOutputPort buscaClienteOutputPort) {
        return new BuscaClientePorUseCase(buscaClienteOutputPort);
    }

    @Bean
    BuscaTodosClientesInputPort buscaTodosClientes(BuscaTodosClientesOutputPort buscaTodosClientesOutputPort) {
        return new BuscaTodosClientesUseCase(buscaTodosClientesOutputPort);
    }

    @Bean
    CadastraClienteInputPort cadastraCliente(CadastraClienteOutputPort cadastraClienteOutputPort) {
        return new CadastraClienteUseCase(cadastraClienteOutputPort);
    }

    @Bean
    CriaQrCodeOutputPort criaQrCodeInputPort() {
        return new PagamentoMock();
    }

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
            CriaQrCodeOutputPort criaQrCodeOutputPort,
            BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        return new CriaCobrancaUseCase(
                criaCobrancaOutputPort,
                criaQrCodeOutputPort,
                buscarPedidoPorIdOutputPort,
                buscaCobrancaOutputPort
        );
    }

    @Bean
    AtualizaStatusCobrancaInputPort atualiStatusCobranca(
            AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort,
            AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort
    ) {
        return new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort,
                atualizaStatusPedidoOutputPort
        );
    }

    @Bean
    BuscaStatusPagamentoInputPort buscaStatusPagamento(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        return new BuscaStatusPagamentoUseCase(buscaStatusPagamentoOutputPort);
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
