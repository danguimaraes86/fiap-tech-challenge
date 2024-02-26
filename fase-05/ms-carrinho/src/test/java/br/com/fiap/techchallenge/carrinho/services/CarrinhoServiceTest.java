package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.functions.EstoquePedidoProducer;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoFinalizadoRepository;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

@SpringBootTest
public class CarrinhoServiceTest {

    @Mock
    CarrinhoRepository carrinhoRepository;

    @Mock
    CarrinhoFinalizadoRepository carrinhoFinalizadoRepository;

    @Mock
    EstoquePedidoProducer estoquePedidoProducer;

    CarrinhoService carrinhoService;

    @BeforeEach
    void setup(){
        carrinhoService = new CarrinhoService(carrinhoRepository, carrinhoFinalizadoRepository, estoquePedidoProducer);
    }

    @Test
    void deveBuscarCarrinhoAberto(){
        Produtos p = geraProduto();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", p);
        carrinhoRepository.save(carrinhoAberto);
        carrinhoService = new CarrinhoService(carrinhoRepository, carrinhoFinalizadoRepository, estoquePedidoProducer);
        Mockito.when(carrinhoService.findCarrinhoOpen(Mockito.any())).thenReturn(new CarrinhoAberto());
    }

    @Test
    void deveAdicionarItemOuCriarCarrinho(){
        Produtos p = geraProduto();
        Mockito.when(carrinhoService.addItemsOuCriarCarrinho("1", p)).thenReturn(new CarrinhoAberto());
    }

    Produtos geraProduto(){
        Produtos produtos = new Produtos("1", 10l);
        return produtos;
    }
}
