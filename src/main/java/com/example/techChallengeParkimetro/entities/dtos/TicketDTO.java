package com.example.techChallengeParkimetro.entities.dtos;

import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.infra.enums.TipoCobranca;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
        UUID uuid,
        LocalDateTime horarioEntrada,
        LocalDateTime horarioSaida,
        TipoCobranca tipoCobranca,
        String permanencia,
        Double valorTotal
) {

    public TicketDTO(Ticket ticket) {
        this(ticket.getUuid(), ticket.getHorarioEntrada(), ticket.getHorarioSaida(), ticket.getTipoCobranca(), ticket.getPermanencia(), ticket.getValorTotal());
    }
}
