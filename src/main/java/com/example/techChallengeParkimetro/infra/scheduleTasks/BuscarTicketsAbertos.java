package com.example.techChallengeParkimetro.infra.scheduleTasks;

import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component @EnableScheduling
public class BuscarTicketsAbertos {

    private final TicketService ticketService;

    public BuscarTicketsAbertos(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1)
    public void buscarTicketsAbertos() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<TicketDTO> ticketsPage = ticketService.findAllTicket(pageable);
        List<TicketDTO> listOpenTicket = ticketsPage.stream().filter(tck -> tck.isEmAberto()).toList();
        System.out.println(listOpenTicket);
    }
}

