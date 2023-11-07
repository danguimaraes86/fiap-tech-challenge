package com.example.techChallengeParkimetro.services;

import com.example.techChallengeParkimetro.entities.Veiculo;
import com.example.techChallengeParkimetro.entities.dtos.VeiculoDTO;
import com.example.techChallengeParkimetro.infra.repositories.VeiculoRepository;
import com.example.techChallengeParkimetro.utils.StringSanitizer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final CondutorService condutorService;

    public VeiculoService(VeiculoRepository veiculoRepository, CondutorService condutorService) {
        this.veiculoRepository = veiculoRepository;
        this.condutorService = condutorService;
    }

    private static Supplier<NoSuchElementException> getVeiculoNaoEncontrado() {
        return () -> new NoSuchElementException("veículo não encontrado");
    }

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Veiculo findById(UUID id) {
        return veiculoRepository.findById(id).orElseThrow(getVeiculoNaoEncontrado());
    }

    public Veiculo findByPlaca(String placa) {
        return veiculoRepository.findVeiculoByPlacaIgnoreCase(placa).orElseThrow(getVeiculoNaoEncontrado());
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
