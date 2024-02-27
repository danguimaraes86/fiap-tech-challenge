package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.functions.EstoquePedidoProducer;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoFinalizadoRepository;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CarrinhoServiceTest {

    @Mock
    CarrinhoRepository carrinhoRepository;

    @Mock
    CarrinhoFinalizadoRepository carrinhoFinalizadoRepository;

    @Mock
    EstoquePedidoProducer estoquePedidoProducer;

    @Mock
    CarrinhoAberto carrinhoAberto;

    CarrinhoService carrinhoService;

    @BeforeEach
    void setup(){
        carrinhoService = new CarrinhoService(carrinhoRepository, carrinhoFinalizadoRepository, estoquePedidoProducer);
    }

    @Test
    void deveLancarExceçãoAoBuscarCarrinhoAberto(){
        Produtos p = geraProduto();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", p);
        carrinhoRepository.save(carrinhoAberto);
        Mockito.when(carrinhoRepository.findById("1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carrinhoService.findCarrinhoOpen("1"))
                .isInstanceOf(NoSuchElementException.class);

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
