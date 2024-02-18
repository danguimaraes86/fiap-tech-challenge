package br.com.fiap.techchallenge.produtos.services;

import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProdutoConsumer {

    private final ProdutoService produtoService;

    public ProdutoConsumer(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Bean(name = "remover-estoque")
    Consumer<ProdutoRequestDTO> consumer(){
        return produtoRequestDTO ->
                produtoService.updateProdutoEstoque(
                        produtoRequestDTO.produtoId(), produtoRequestDTO.estoque());
    }


}
