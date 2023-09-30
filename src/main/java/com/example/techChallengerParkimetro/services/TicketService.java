package com.example.techChallengerParkimetro.services;

import com.example.techChallengerParkimetro.entities.Ticket;
import com.example.techChallengerParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengerParkimetro.infra.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Page<TicketDTO> findAllTicket(Pageable pageable){
        var tickets = ticketRepository.findAll(pageable);

        Page<TicketDTO> ticketsDto = tickets.map(TicketDTO::new);
        return ticketsDto;
    }

    public TicketDTO findById(UUID uuid){
        var ticket = ticketRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado"));

        return new TicketDTO(ticket);
    }

    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket tiket = new Ticket(LocalDateTime.now(), ticketDTO.tipoCobranca());

        var ticktCreated = ticketRepository.save(tiket);
        System.out.println(tiket.getTipoCobranca().getPeriodo());

        return new TicketDTO(ticktCreated);
    }

    public TicketDTO emitirRecibo(UUID uuid){
        var ticket = ticketRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado"));

        var valor = ticket.getTipoCobranca().executar(ticket.getHorarioEntrada());
        ticket.setValorTotal(valor);
        ticket.setHorarioSaida(LocalDateTime.now());

        ticket.setPermanencia(ticket.getHorarioEntrada().until(ticket.getHorarioSaida(), ChronoUnit.HOURS) + " " + ticket.getTipoCobranca().getPeriodo());

        System.out.println(ticket.getPermanencia());

        return new TicketDTO(ticket);
    }
}
