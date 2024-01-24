package com.example.techChallengeParkimetro.controllers;


import com.example.techChallengeParkimetro.entities.Ticket;
import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> findAll(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "qtdItens", defaultValue = "3") int qtdItens
    ) {
        Pageable pageable = PageRequest.of(pagina, qtdItens);
        Page<TicketDTO> tickets = ticketService.findAllTicket(pageable).map(Ticket::toDTO);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TicketDTO> findById(@PathVariable UUID uuid) {
        Ticket ticket = ticketService.findById(uuid);
        return ResponseEntity.ok(ticket.toDTO());
    }

    @PostMapping("/registrarEntrada")
    public ResponseEntity<TicketDTO> registrarEntrada(@RequestBody @Valid TicketDTO ticketDTO) {
        Ticket ticket = ticketService.registarEntrada(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket.toDTO());
    }

    @PostMapping("/registrarSaida")
    public ResponseEntity<TicketDTO> registrarSaida(@RequestBody @Valid TicketDTO ticketDTO) {
        Ticket ticket = ticketService.registrarSaida(ticketDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(ticket.toDTO());
    }

    @GetMapping("/emitirRecibo/{uuid}")
    public ResponseEntity<TicketDTO> emitirRecibo(@PathVariable UUID uuid) {
        Ticket recibo = ticketService.emitirRecibo(uuid);
        return ResponseEntity.ok(recibo.toDTO());
    }
}
