package br.com.fiap.techchallenge.servicocobranca.bdd;

import br.com.fiap.techchallenge.servicocobranca.*;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class StepDefinition {

    private Response response;
    private ProdutoResponse produtoResponse;
    private ClienteResponse clienteResponse;
    private PedidoResponse pedidoResponse;
    private CobrancaResponse cobrancaResponse;

    @Quando("preencher todos os dados para cadastro do produto")
    public ProdutoResponse preencherTodosDadosParaCadastrarProduto() {
        var produtoRequest = ProdutoTestBase.criarProdutoRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(produtoRequest)
                .when()
                .post("http://mock:8081/produtos");
        return response.then()
                .extract()
                .as(ProdutoResponse.class);
    }

    @Dado("que um produto já está cadastrado")
    public void produtoJaCadastrado() {
        produtoResponse = preencherTodosDadosParaCadastrarProduto();
    }

    @Quando("preencher todos os dados para cadastro do cliente")
    public ClienteResponse preencherTodosDadosParaCadastrarCliente() {
        var clienteRequest = ClienteTestBase.criarClienteRequest();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clienteRequest)
                .when()
                .post("http://mock:8081/clientes");
        return response.then()
                .extract()
                .as(ClienteResponse.class);
    }

    @Dado("que um cliente já está cadastrado")
    public void clienteJaCadastrado() {
        clienteResponse = preencherTodosDadosParaCadastrarCliente();
    }

    @Quando("preencher todos os dados para cadastro do pedido")
    public PedidoResponse preencherTodosDadosParaCadastrarPedido() {
        var pedidoRequest = PedidoTestBase.criarPedidoRequest(clienteResponse.getId(), produtoResponse.getId());
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(pedidoRequest)
                .when()
                .post("http://mock:8081/pedidos");
        return response.then()
                .extract()
                .as(PedidoResponse.class);
    }

    @Dado("que um pedido já está cadastrado")
    public void pedidoJaCadastrado() {
        pedidoResponse = preencherTodosDadosParaCadastrarPedido();
    }

    @Quando("realizar a busca da cobrança por pedido")
    public void realizarBuscaCobrancaPorPedido() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cobrancas/{id}/pedido", pedidoResponse.getId());
    }

    @Quando("preencher todos os dados para cadastro da cobrança")
    public CobrancaResponse preencherTodosDadosParaCadastrarCobranca() {
        var cobrancaRequest = CobrancaTestBase.criarCobrancaRequest(pedidoResponse.getId());
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cobrancaRequest)
                .when()
                .post("/cobrancas");
        return response.then()
                .extract()
                .as(CobrancaResponse.class);
    }

    @Então("a cobrança deve ser criada com sucesso")
    public void cobrancaDeveSerCriadaComSucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Então("deve exibir a cobrança cadastrada")
    public void deveExibirCobrancaCadastrada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"));
    }

    @Dado("que uma cobrança já está cadastrada")
    public void cobrancaJaCadastrada() {
        cobrancaResponse = preencherTodosDadosParaCadastrarCobranca();
    }

    @Quando("realizar a busca da cobrança")
    public void realizarBuscaCobranca() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/cobrancas/{id}", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser exibida com sucesso")
    public void cobrancaDeveSerExibidaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"));
    }

    @Quando("realizar a requisição para alterar a cobrança")
    public void realizarRequisicaoParaAlterarCobranca() {
        var atualizaStatusCobrancaRequest = AtualizaStatusCobrancaTestBase
                .criarAtualizaStatusCobrancaRequest(StatusCobrancaEnum.PAGO);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(atualizaStatusCobrancaRequest)
                .when()
                .put("/cobrancas/{id}/status", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser alterada com sucesso")
    public void cobrancaDeveSerAlteradaComSucesso() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @Então("deve exibir a cobrança alterada")
    public void deveExibirCobrancaAlterada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("./schemas/CobrancaResponseSchema.json"))
                .body("status", equalTo(StatusCobrancaEnum.PAGO.name()));
    }

    @Quando("realizar a requisição para alterar a cobrança no Mercado Pago")
    public void realizarRequisicaoParaAlterarCobrancaMercadoPago() {
        var webhookStatusCobrancaRequest = WebhookStatusCobrancaTestBase
                .criarWebhookStatusCobrancaRequest("approved", 1L);
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(webhookStatusCobrancaRequest)
                .when()
                .put("/cobrancas/{id}/webhook-status", cobrancaResponse.getId());
    }

    @Então("a cobrança deve ser alterada com sucesso no Mercado Pago")
    public void cobrancaDeveSerAlteradaComSucessoMercadoPago() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Então("a cobrança deve ser exibida com resposta vazia")
    public void cobrancaDeveSerExibidaComRespostaVazia() {
        response.then()
                .body(is(emptyString()));
    }

}
