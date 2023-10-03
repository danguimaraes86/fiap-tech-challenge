package com.example.techChallengeParkimetro.controllers;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
import com.example.techChallengeParkimetro.services.CondutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<CondutorDTO> createCondutor(@RequestBody @Valid CondutorDTO condutorDTO) {
        Condutor condutor = condutorService.createCondutor(condutorDTO.toEntity());
        return ResponseEntity.ok(condutor.toDTO());
    }
}
