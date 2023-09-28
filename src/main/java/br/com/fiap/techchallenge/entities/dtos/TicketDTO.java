package br.com.fiap.techchallenge.entities.dtos;

import br.com.fiap.techchallenge.infra.enums.FormaPagamento;
import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.infra.enums.TipoCobranca;

import java.time.LocalDateTime;
import java.util.Date;
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
