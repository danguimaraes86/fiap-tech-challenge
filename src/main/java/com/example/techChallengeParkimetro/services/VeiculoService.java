package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.infra.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo createVeiculo(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }
}
