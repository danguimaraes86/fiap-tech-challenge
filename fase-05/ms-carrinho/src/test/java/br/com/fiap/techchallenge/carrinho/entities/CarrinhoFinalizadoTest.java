package br.com.fiap.techchallenge.carrinho.entities;

import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;

@SpringBootTest
class CarrinhoFinalizadoTest {

    @Test
    void constructorTest() {
        Produto produto = new Produto("1", 10L);
        CarrinhoFinalizado car = new CarrinhoFinalizado("oi", "1", produto, null, false, Status.AGUARDANDOPAGAMENTO);
        car.getUsuarioId();
        car.setDataDoPagamento(LocalDateTime.now());
        car.setStatusPagamento(true);
        car.setStatusDoPedido(Status.PAGAMENTOAPROVADO);
        Status.PAGAMENTOAPROVADO.getMensagem();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", Collections.singleton(produto));
        CarrinhoFinalizado carrinho = new CarrinhoFinalizado(carrinhoAberto);
    }
}
