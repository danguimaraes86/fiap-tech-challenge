package br.com.fiap.techchallenge.carrinho.controllers;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produtos;
import br.com.fiap.techchallenge.carrinho.services.CarrinhoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }


    @PostMapping("/adicionarproduto")
    public CarrinhoAberto adicionarProduto(@RequestHeader String usuarioId, @RequestBody Produtos produtos){


        return carrinhoService.addItemsOuCriarCarrinho(usuarioId, produtos);
    }

    @PostMapping("/finalizarcompra")
    public boolean finalizarCompra(@RequestHeader String usuarioId){

        CarrinhoFinalizado carrinhoFinalizado = carrinhoService.efetuandoCompraDoCarrrinho(usuarioId);

        return true;
    }
}
