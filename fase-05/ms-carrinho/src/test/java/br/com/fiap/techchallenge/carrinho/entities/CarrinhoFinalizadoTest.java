package br.com.fiap.techchallenge.carrinho.entities;

import br.com.fiap.techchallenge.carrinho.entities.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CarrinhoFinalizadoTest {

    @Test
    void constructorTest(){
        Produtos produto = new Produtos("1", 10l);
        CarrinhoFinalizado car = new CarrinhoFinalizado("oi", "1", produto, null, false, Status.AGUARDANDOPAGAMENTO);
        car.getUsuarioId();
        car.setDataDoPagamento(LocalDateTime.now());
        car.setStatusPagamento(true);
        car.setStatusDoPedido(Status.PAGAMENTOAPROVADO);
        Status.PAGAMENTOAPROVADO.getMensagemDoStatus();
        CarrinhoAberto carrinhoAberto = new CarrinhoAberto("1", produto);
        CarrinhoFinalizado carrinho = new CarrinhoFinalizado(carrinhoAberto);
    }
}
