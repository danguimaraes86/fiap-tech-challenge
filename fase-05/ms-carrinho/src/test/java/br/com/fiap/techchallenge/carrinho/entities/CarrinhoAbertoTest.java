package br.com.fiap.techchallenge.carrinho.entities;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CarrinhoAbertoTest {


    @Test
    void carrinhoTest(){
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto();
        Set<Produtos> produtosSet = new HashSet<>();
        Produtos produtos = new Produtos("1", 10l);
        produtosSet.add(produtos);
        carrinhoAberto.setProdutos(produtosSet);
        carrinhoAberto.addProduto(produtos);
    }

    @Test
    void constructorTest(){
        Produtos produtos = new Produtos("1", 10l);
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", produtos);
        CarrinhoAberto carrinhoAberto2 = new CarrinhoAberto("2");
        carrinhoAberto2.getUsuarioId();
        carrinhoAberto.getProdutos();
    }
}
