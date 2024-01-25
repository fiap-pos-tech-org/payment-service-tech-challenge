package br.com.fiap.techchallenge.servicopagamento;

import br.com.fiap.techchallenge.servicopagamento.adapters.web.models.requests.ProdutoRequest;
import br.com.fiap.techchallenge.servicopagamento.core.domain.entities.enums.CategoriaEnum;

import java.math.BigDecimal;

public class ProdutoTestBase {

    private ProdutoTestBase() {
    }

    public static ProdutoRequest criarProdutoRequest() {
        var produtoRequest = new ProdutoRequest();
        produtoRequest.setNome("X-Tudo");
        produtoRequest.setPreco(BigDecimal.valueOf(10.5));
        produtoRequest.setDescricao("X-Tudo Monstrão");
        produtoRequest.setCategoria(CategoriaEnum.LANCHE);
        return produtoRequest;
    }

}
