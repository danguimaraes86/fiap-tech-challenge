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

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void deveCriarCarrinhoAoNaoEncontrarUmAberto(){
        Produtos p = geraProduto();
        Mockito.when(carrinhoService.addItemsOuCriarCarrinho("1", p)).thenReturn(new CarrinhoAberto());
    }

    @Test
    void deveAdicionarItemAoCarrinhoAberto(){
        Produtos p = geraProduto();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", p);
        Mockito.when(carrinhoRepository.save(carrinhoAberto)).thenReturn(carrinhoAberto);
        Mockito.when(carrinhoRepository.findById("1")).thenReturn(Optional.of(carrinhoAberto));
        var car = carrinhoService.addItemsOuCriarCarrinho(carrinhoAberto.getUsuarioId(), p);
        assertThat(car).isNotNull();
        Assertions.assertEquals( "1", car.getUsuarioId());
    }

    @Test
    void deveLançarExceçãoAoNãoAcharEstoque(){
        CarrinhoAberto car = geraCarrinhoAberto();
        Mockito.when(carrinhoRepository.save(car)).thenReturn(car);
        Mockito.when(carrinhoRepository.findById("1")).thenReturn(Optional.of(car));
        assertThatThrownBy(() -> carrinhoService.efetuandoCompraDoCarrrinho(car.getUsuarioId()))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void deveDeletarCarrinho(){
        var car = geraCarrinhoAberto();
        carrinhoService.deletarCarrinho(car.getUsuarioId());
    }

    CarrinhoAberto geraCarrinhoAberto(){
        Produtos p = geraProduto();
        CarrinhoAberto car = new CarrinhoAberto("1", p);
        return car;
    }
    Produtos geraProduto(){
        Produtos produtos = new Produtos("1", 2l);
        return produtos;
    }
}
