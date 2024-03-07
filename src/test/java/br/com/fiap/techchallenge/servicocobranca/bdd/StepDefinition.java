package br.com.fiap.techchallenge.servicocobranca.bdd;

import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ClienteResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.CobrancaResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.PedidoResponse;
import br.com.fiap.techchallenge.servicocobranca.adapters.web.models.responses.ProdutoResponse;
import br.com.fiap.techchallenge.servicocobranca.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.servicocobranca.utils.AtualizaStatusCobrancaHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.CobrancaHelper;
import br.com.fiap.techchallenge.servicocobranca.utils.WebhookStatusCobrancaHelper;
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

    @Dado("que um produto já está cadastrado")
    public void produtoJaCadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("http://localhost:8081/produtos/1");

        produtoResponse = response.then()
                .extract()
                .as(ProdutoResponse.class);
    }

    @Dado("que um cliente já está cadastrado")
    public void clienteJaCadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("http://localhost:8081/clientes/1");

        clienteResponse = response.then()
                .extract()
                .as(ClienteResponse.class);
    }

    @Dado("que um pedido já está cadastrado")
    public void pedidoJaCadastrado() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("http://localhost:8081/pedidos/1");

        pedidoResponse = response.then()
                .extract()
                .as(PedidoResponse.class);
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
        var cobrancaRequest = CobrancaHelper.criarCobrancaRequest(pedidoResponse.getId());
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
        var atualizaStatusCobrancaRequest = AtualizaStatusCobrancaHelper
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
        var webhookStatusCobrancaRequest = WebhookStatusCobrancaHelper
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
