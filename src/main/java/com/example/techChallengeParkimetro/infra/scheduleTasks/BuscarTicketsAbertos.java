package com.example.techChallengeParkimetro.infra.scheduleTasks;

import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.infra.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class BuscarTicketsAbertos {
    private final TicketRepository ticketRepository;
    @Autowired
    public BuscarTicketsAbertos(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
    public void buscarTicketsAbertos() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<Ticket> listOpenTicket = tickets.stream().filter(tck -> tck.isEmAberto()).toList();
        listOpenTicket.forEach(x -> x.getTipoCobranca().notificar(x.getHorarioEntrada()));
    }
}

