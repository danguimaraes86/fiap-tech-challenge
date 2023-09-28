package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.entities.dtos.TicketDTO;
import br.com.fiap.techchallenge.infra.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public TicketDTO findAllTicket(Pageable pageable){
        Page<Ticket> tickets = ticketRepository.findAll(pageable);
        return tickets;
    }
}
