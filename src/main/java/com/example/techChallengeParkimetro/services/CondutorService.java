package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Condutor;
import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.CondutorDTO;
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

    public Condutor findById(UUID id) {
        return condutorRepository.findById(id).orElseThrow();
    }

    public Condutor findCondutorByCpf(String cpf) {
        return condutorRepository.findCondutorByCpf(cpf).orElseThrow();
    }

    public Condutor create(Condutor condutor) {
        condutor.limparCpfCelular();
        return condutorRepository.save(condutor);
    }

    public void delete(String cpf) {
        condutorRepository.delete(findCondutorByCpf(cpf));
    }

    public Condutor update(String cpf, CondutorDTO condutorDTO) {
        Condutor condutorByCpf = findCondutorByCpf(cpf);
        condutorByCpf.update(condutorDTO);
        return condutorRepository.save(condutorByCpf);
    }

    public void vincularVeiculo(String condutorCpf, Veiculo veiculo) {
        Condutor condutor = findCondutorByCpf(condutorCpf);
        condutor.vincularVeiculo(veiculo);
        condutorRepository.save(condutor);
    }
}
