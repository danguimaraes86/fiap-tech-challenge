package br.com.fiap.techchallenge.carrinho.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class CarrinhoAbertoTest {

    @Test
    void carrinhoTest() {
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto();
        Set<Produto> produtosSet = new HashSet<>();
        Produto produto = new Produto("1", 10l);
        produtosSet.add(produto);
        carrinhoAberto.setProdutos(produtosSet);
        carrinhoAberto.addProduto(produto);
    }

    @Test
    void constructorTest() {
        Produto produto = new Produto("1", 10l);
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", Collections.singleton(produto));
        CarrinhoAberto carrinhoAberto2 = new CarrinhoAberto("2");
        carrinhoAberto2.getUsuarioId();
        carrinhoAberto.getProdutos();
    }
}
