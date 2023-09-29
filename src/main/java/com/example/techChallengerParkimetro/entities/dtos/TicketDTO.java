package com.example.techChallengerParkimetro.entities.dtos;

import com.example.techChallengerParkimetro.entities.Ticket;
import com.example.techChallengerParkimetro.infra.enums.TipoCobranca;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketDTO(
        UUID uuid,
        LocalDateTime horarioEntrada,
        LocalDateTime horarioSaida,
        TipoCobranca tipoCobranca,
        Double valorTotal
) {

    public TicketDTO(Ticket ticket){
        this(ticket.getUuid(), ticket.getHorarioEntrada(), ticket.getHorarioSaida(), ticket.getTipoCobranca(), ticket.getValorTotal());
    }
}
