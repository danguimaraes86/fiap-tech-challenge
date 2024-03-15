package br.com.fiap.techchallenge.carrinho.controllers;

import br.com.fiap.techchallenge.carrinho.entities.CarrinhoAberto;
import br.com.fiap.techchallenge.carrinho.entities.CarrinhoFinalizado;
import br.com.fiap.techchallenge.carrinho.entities.Produto;
import br.com.fiap.techchallenge.carrinho.services.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PostMapping("/novo")
    public ResponseEntity<String> createNovoCarrinho(@RequestBody String email) {
        CarrinhoAberto carrinho = carrinhoService.criarNovoCarrinho(email);
        return ResponseEntity.ok(carrinho.getUsuarioId());
    }

    @GetMapping
    public ResponseEntity<CarrinhoAberto> findCarrinhoAberto(JwtAuthenticationToken jwtToken) {
        return ResponseEntity.ok(carrinhoService.findCarrinhoOpen(jwtToken));
    }

    @PostMapping("/adicionarproduto")
    public ResponseEntity<CarrinhoAberto> adicionarProduto(JwtAuthenticationToken jwtToken, @RequestBody Produto produto) {
        return ResponseEntity.ok(carrinhoService.addItems(jwtToken, produto));
    }

    @PostMapping("/finalizarcompra")
    public ResponseEntity<CarrinhoFinalizado> finalizarCompra(JwtAuthenticationToken jwtToken) {
        CarrinhoFinalizado carrinhoFinalizado = carrinhoService.efetuandoCompraDoCarrrinho(jwtToken);
        return ResponseEntity.ok(carrinhoFinalizado);
    }
}
