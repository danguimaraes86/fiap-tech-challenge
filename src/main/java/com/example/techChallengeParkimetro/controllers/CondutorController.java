package com.example.techChallengeParkimetro.controllers;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
import com.example.techChallengeParkimetro.services.CondutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<CondutorDTO>> findAll() {
        List<Condutor> condutorList = condutorService.findAll();
        return ResponseEntity.ok(condutorList.stream().map(Condutor::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CondutorDTO> findById(@PathVariable UUID id) {
        Condutor condutor = condutorService.findCondutorById(id);
        return ResponseEntity.ok(condutor.toDTO());
    }

    @PostMapping
    public ResponseEntity<CondutorDTO> createCondutor(@RequestBody @Valid CondutorDTO condutorDTO) {
        Condutor condutor = condutorService.createCondutor(condutorDTO.toEntity());
        return ResponseEntity.ok(condutor.toDTO());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<CondutorDTO> updateCondutor(@PathVariable String cpf, @RequestBody CondutorDTO condutorDTO) {
        Condutor condutor = condutorService.updateCondutor(cpf, condutorDTO);
        return ResponseEntity.ok(condutor.toDTO());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteCondutor(@PathVariable String cpf) {
        condutorService.deleteCondutor(cpf);
        return ResponseEntity.noContent().build();
    }
}
