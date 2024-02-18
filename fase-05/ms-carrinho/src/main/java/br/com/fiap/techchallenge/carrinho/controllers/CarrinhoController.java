package br.com.fiap.techchallenge.carrinho.controllers;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.services.CarrinhoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("finalizarcompra")
    public boolean finalizarCompra(String usuarioId){

        CarrinhoFinalizado carrinhoFinalizado = carrinhoService.efetuandoCompraDoCarrrinho(usuarioId);

        return true;
    }
}
