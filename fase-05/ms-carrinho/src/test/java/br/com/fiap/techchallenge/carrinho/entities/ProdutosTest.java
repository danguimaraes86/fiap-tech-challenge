package br.com.fiap.techchallenge.carrinho.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProdutosTest {

    @Test
    void produtoTest(){
        Produtos p = new Produtos("1", 10l);
        p.getProdutoId();
        p.getQuantidade();
        p.setQuantidade(1l);
        p.addQuantidade(5l);
    }
}
