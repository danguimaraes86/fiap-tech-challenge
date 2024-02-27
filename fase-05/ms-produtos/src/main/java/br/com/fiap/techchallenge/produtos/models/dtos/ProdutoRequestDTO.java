package br.com.fiap.techchallenge.produtos.models.dtos;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public record ProdutoRequestDTO(
        String produtoId,
        Long quantidade

) {

}
