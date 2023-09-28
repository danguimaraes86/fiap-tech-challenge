package br.com.fiap.techchallenge.entities.dtos;

import br.com.fiap.techchallenge.entities.FormaPagamento;

public record TicketDTO(
        String nome,
        String cpf,
        String email,
        String celular,
        FormaPagamento formaPagamento
) {
}
