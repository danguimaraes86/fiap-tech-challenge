package com.example.techChallengeParkimetro.entities.dtos;
import com.example.techChallengeParkimetro.infra.enums.FormaPagamento;

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
