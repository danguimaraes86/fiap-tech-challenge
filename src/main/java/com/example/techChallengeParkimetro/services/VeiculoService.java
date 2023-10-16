package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.VeiculoDTO;
import com.example.techChallengeParkimetro.infra.repositories.VeiculoRepository;
import com.example.techChallengeParkimetro.utils.StringSanitizer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final CondutorService condutorService;

    public VeiculoService(VeiculoRepository veiculoRepository, CondutorService condutorService) {
        this.veiculoRepository = veiculoRepository;
        this.condutorService = condutorService;
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

    public Veiculo create(VeiculoDTO veiculoDTO, String condutorCpf) {
        try {
            Veiculo veiculo = veiculoRepository.save(
                    veiculoDTO.toEntity(StringSanitizer.somenteNumeros(condutorCpf)));
            condutorService.vincularVeiculo(condutorCpf, veiculo);
            return veiculo;
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("placa já cadastrada");
        }
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
