package br.com.fiap.techchallenge.carrinho.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProdutoTest {

    @Test
    void produtoTest() {
        Produto p = new Produto("1", 10l);
        p.getProdutoId();
        p.getQuantidade();
        p.setQuantidade(1l);
        p.addQuantidade(5l);
    }
}
