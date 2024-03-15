package br.com.fiap.techchallenge.carrinho.services;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.Produto;
import br.com.fiap.techchallenge.carrinho.feignclients.ProdutoFeignClient;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoFinalizadoRepository;
import br.com.fiap.techchallenge.carrinho.repository.CarrinhoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class CarrinhoServiceTest {

    @Mock
    CarrinhoRepository carrinhoRepository;
    @Mock
    CarrinhoFinalizadoRepository carrinhoFinalizadoRepository;
    @Mock
    ProdutoFeignClient produtoFeignClient;
    CarrinhoService carrinhoService;

    @BeforeEach
    void setup() {
        carrinhoService = new CarrinhoService(carrinhoRepository, carrinhoFinalizadoRepository, produtoFeignClient);
    }

    @Test
    void deveLancarExcecaoAoBuscarCarrinhoAberto() {
        Produto p = geraProduto();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", Collections.singleton(p));
        carrinhoRepository.save(carrinhoAberto);
        when(carrinhoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carrinhoService.findCarrinhoOpen(mock(JwtAuthenticationToken.class)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deveAdicionarItemAoCarrinhoAberto() {
        Produto p = geraProduto();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", Collections.singleton(p));
        when(carrinhoRepository.save(carrinhoAberto)).thenReturn(carrinhoAberto);
        when(carrinhoRepository.findById(anyString())).thenReturn(Optional.of(carrinhoAberto));
        var car = carrinhoService.addItems(mock(JwtAuthenticationToken.class), p);
        assertThat(car).isNotNull();
        Assertions.assertEquals("1", car.getUsuarioId());
    }

    @Test
    void deveLançarExceçãoAoNãoAcharEstoque() {
        CarrinhoAberto car = geraCarrinhoAberto();
        when(carrinhoRepository.save(car)).thenReturn(car);
        when(carrinhoRepository.findById(anyString())).thenReturn(Optional.of(car));
        assertThatThrownBy(() -> carrinhoService.efetuandoCompraDoCarrrinho(mock(JwtAuthenticationToken.class)))
                .isInstanceOf(RuntimeException.class);
    }

    CarrinhoAberto geraCarrinhoAberto() {
        Produto p = geraProduto();
        return new CarrinhoAberto("1", Collections.singleton(p));
    }

    Produto geraProduto() {
        return new Produto("1", 2L);
    }
}
