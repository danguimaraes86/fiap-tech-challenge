package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.infra.enums.TipoCobranca;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
        UUID uuid,
        LocalDateTime horarioEntrada,
        LocalDateTime horarioSaida,
        String tipoCobranca,
        String permanencia,
        Double valorTotal,
        @NotBlank
        String condutorCpf,
        @NotBlank
        String placaVeiculo
) {
    public Ticket toEntity(String condutor, String veiculo, LocalDateTime horarioEntrada, TipoCobranca tipoCobranca) {
        return new Ticket(condutor, veiculo, horarioEntrada, tipoCobranca);
    }

    public Boolean isEmAberto() {
        return horarioSaida == null;
    }
}
