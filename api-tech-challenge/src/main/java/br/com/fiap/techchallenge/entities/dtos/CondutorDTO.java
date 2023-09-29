package br.com.fiap.techchallenge.entities.dtos;

import br.com.fiap.techchallenge.infra.enums.FormaPagamento;

import java.util.UUID;

public record CondutorDTO (
        UUID uuid,
        String nome,
        String cpf,
        String email,
        String celular,
        FormaPagamento formaPagamento
){
}
