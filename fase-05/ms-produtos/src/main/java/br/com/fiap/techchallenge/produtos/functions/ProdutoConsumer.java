package br.com.fiap.techchallenge.produtos.functions;

import br.com.fiap.techchallenge.produtos.models.dtos.ProdutoRequestDTO;
import br.com.fiap.techchallenge.produtos.services.ProdutoService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Component
public class ProdutoConsumer {

    private final ProdutoService produtoService;

    public ProdutoConsumer(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @Bean(name = "removerestoque")
    Consumer<ProdutoRequestDTO> consumer(){


        return produtoRequestDTO ->
                produtoService.updateProdutoEstoque(
                        produtoRequestDTO.produtoId(), produtoRequestDTO.quantidade());
    }


}
