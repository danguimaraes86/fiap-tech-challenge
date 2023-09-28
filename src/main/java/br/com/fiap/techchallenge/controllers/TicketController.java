package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.entities.dtos.TicketDTO;
import br.com.fiap.techchallenge.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        Pageable pageable = new PageRequest(pagina, qtdItens);

        var tickets = ticketService.findAllTicket(pageable);

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TicketDTO> findById(@PathVariable UUID uuid){


        return ResponseEntity.ok();
    }

    @PostMapping
    public ResponseEntity<TicketDTO> create(@RequestBody TicketDTO ticketDTO){


        return ResponseEntity.created().build();
    }

    @PutMapping
    public ResponseEntity<TicketDTO> update(@RequestBody TicketDTO ticketDTO){

        return ResponseEntity.ok();
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }
}
