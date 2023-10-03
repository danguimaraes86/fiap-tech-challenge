package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.infra.repositories.CondutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CondutorService {

    private final CondutorRepository condutorRepository;

    public CondutorService(CondutorRepository condutorRepository) {
        this.condutorRepository = condutorRepository;
    }

    public List<Condutor> findAll() {
        return condutorRepository.findAll();
    }

    public Condutor createCondutor(Condutor condutor) {
        return condutorRepository.save(condutor);
    }
}
