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

    public TicketDTO createTicket(TicketDTO ticketDTO){
        Ticket tiket = new Ticket(LocalDateTime.now(), ticketDTO.tipoCobranca());

        System.out.println("Ola 2");
        var ticktCreated = ticketRepository.save(tiket);

        return new TicketDTO(ticktCreated);
    }

    public TicketDTO emitirRecibo(UUID uuid){
        var ticket = ticketRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Ticket não encontrado"));

        ticket.setHorarioSaida(LocalDateTime.now());
        var valor = ticket.getTipoCobranca().executar(ticket.getHorarioEntrada(), ticket.getHorarioSaida());
        ticket.setValorTotal(valor);

        return new TicketDTO(ticket);
    }

}
