package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.infra.enums.FormaPagamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CondutorDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CPF(message = "CPF inválido")
        String cpf,
        @NotBlank
        @Email
        String email,
        String celular,
        @NotBlank
        String formaPagamento,
        List<VeiculoDTO> veiculoList
) {
    public Condutor toEntity() {
        return new Condutor(nome, cpf, email, celular, FormaPagamento.valueOf(formaPagamento.toUpperCase()));
    }
}
