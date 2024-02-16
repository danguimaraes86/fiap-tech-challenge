package br.com.fiap.techchallenge.produtos.model;

import br.com.fiap.techchallenge.produtos.models.Produto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProdutoTest {

    @Test
    void deveCriarObjeto_comSucesso() {
        Produto produto = new Produto();
        assertThat(produto).isInstanceOf(Produto.class);
    }
}
