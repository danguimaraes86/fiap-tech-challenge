package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.infra.repositories.CondutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CondutorService {

    private final CondutorRepository condutorRepository;

    public CondutorService(CondutorRepository condutorRepository) {
        this.condutorRepository = condutorRepository;
    }

    public List<Condutor> findAll() {
        return condutorRepository.findAll();
    }

    public Condutor findCondutorById(UUID id) {
        return condutorRepository.findById(id).orElseThrow();
    }

    public Condutor findCondutorByCpf(String cpf) {
        return condutorRepository.findCondutorByCpf(cpf).orElseThrow();
    }

    public Condutor createCondutor(Condutor condutor) {
        return condutorRepository.save(condutor);
    }

    public void deleteCondutor(String cpf) {
        condutorRepository.delete(findCondutorByCpf(cpf));
    }
}
