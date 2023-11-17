package br.com.fiap.techchallenge.controllers;


import br.com.fiap.techchallenge.entities.Condutor;
import br.com.fiap.techchallenge.entities.Ticket;
import br.com.fiap.techchallenge.entities.dtos.CondutorDTO;
import br.com.fiap.techchallenge.entities.dtos.TicketDTO;
import br.com.fiap.techchallenge.services.CondutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequestMapping("/condutor")
@RestController
public class CondutorController {

    private final CondutorService condutorService;


    public CondutorController(CondutorService condutorService) {
        this.condutorService = condutorService;
    }

    @GetMapping
    public ResponseEntity<List<CondutorDTO>> findAll(
            @RequestParam(required = false) HashMap<String, String> params
    ) {
        if (params.isEmpty()) {
            return ResponseEntity.ok(condutorService.findAll()
                    .stream().map(Condutor::toDTO).toList()
            );
        }
        return ResponseEntity.ok().body(condutorService.findByAtributo(params)
                .stream().map(Condutor::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondutorDTO> findById(@PathVariable UUID id) {
        Condutor condutor = condutorService.findById(id);
        return ResponseEntity.ok(condutor.toDTO());
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> buscarTickets(@RequestParam String condutorCPF) {
        List<Ticket> tickets = condutorService.buscarTickets(condutorCPF);
        return ResponseEntity.ok().body(tickets.stream().map(Ticket::toDTO).toList());
    }

    @PostMapping
    public ResponseEntity<CondutorDTO> createCondutor(@RequestBody @Valid CondutorDTO condutorDTO) {
        Condutor condutor = condutorService.create(condutorDTO.toEntity());
        return ResponseEntity.ok(condutor.toDTO());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<CondutorDTO> updateCondutor(@PathVariable String cpf, @RequestBody CondutorDTO condutorDTO) {
        Condutor condutor = condutorService.update(cpf, condutorDTO);
        return ResponseEntity.ok(condutor.toDTO());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteCondutor(@PathVariable String cpf) {
        condutorService.delete(cpf);
        return ResponseEntity.noContent().build();
    }
}
