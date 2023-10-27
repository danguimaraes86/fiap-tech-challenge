package com.example.techChallengeParkimetro.infra.scheduleTasks;

import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.infra.repositories.TicketRepository;
import com.example.techChallengeParkimetro.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduleTasks {
    private final TicketService ticketService;

    @Autowired
    public ScheduleTasks(TicketRepository ticketRepository, TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 30)
    public void buscarTicketsAbertos() {
        List<Ticket> tickets = ticketService.findTicketsAbertos();
        tickets.forEach(ticket -> System.out.println(ticket.toString()));
    }
}
