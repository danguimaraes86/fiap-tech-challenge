package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.entities.dtos.TicketDTO;
import br.com.fiap.techchallenge.infra.repositories.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        var ticket = ticketRepository.findById(uuid).orElseThrow(x -> throw new EntityNotFoundException("Ticket não encontrado"));

        return new TicketDTO(ticket);
    }

    public TicketDTO createTicket(TicketDTO ticketDTO){
        var ticktCreated = ticketRepository.save(new Ticket(ticketDTO));

        return new TicketDTO(ticktCreated);
    }

    public TicketDTO updateTicket(TicketDTO ticketDTO){

    }

}
