package com.example.techChallengeParkimetro.controllers;


import com.example.techChallengeParkimetro.entities.dtos.TicketDTO;
import com.example.techChallengeParkimetro.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("ticket")
public class TicketController {
    private final TicketService ticketService;
    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> findAll(@RequestParam (value = "pagina", defaultValue = "0") int pagina,
                                                   @RequestParam (value = "qtdItens", defaultValue = "3") int qtdItens){

        Pageable pageable = PageRequest.of(pagina, qtdItens);

        var tickets = ticketService.findAllTicket(pageable);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TicketDTO> findById(@PathVariable UUID uuid){
        var ticket = ticketService.findById(uuid);

        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    public ResponseEntity<TicketDTO> create(@RequestBody TicketDTO ticketDTO){

        var ticketCreated = ticketService.createTicket(ticketDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCreated);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<TicketDTO> emitirRecibo(@PathVariable UUID uuid){
        var recibo = ticketService.emitirRecibo(uuid);

        return ResponseEntity.ok(recibo);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }
}
