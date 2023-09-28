package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.entities.dtos.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> findAll(){

        return
    }
}
