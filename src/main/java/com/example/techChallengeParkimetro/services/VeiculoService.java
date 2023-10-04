package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.VeiculoDTO;
import com.example.techChallengeParkimetro.infra.repositories.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo findById(UUID id) {
        return veiculoRepository.findById(id).orElseThrow();
    }

    public Veiculo findByPlaca(String placa) {
        return veiculoRepository.findVeiculoByPlaca(placa).orElseThrow();
    }

    public Veiculo create(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public Veiculo update(String placa, VeiculoDTO veiculoDTO) {
        Veiculo veiculo = findByPlaca(placa);
        veiculo.update(veiculoDTO);
        return veiculoRepository.save(veiculo);
    }

    public void delete(String placa) {
        veiculoRepository.delete(findByPlaca(placa));
    }
}
