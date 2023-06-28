package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.service;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.entity.Eletrodomestico;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.repository.EletrodomesticoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EletrodomesticoService {

    private final EletrodomesticoRepository eletrodomesticoRepository;

    @Autowired
    public EletrodomesticoService(EletrodomesticoRepository eletrodomesticoRepository) {
        this.eletrodomesticoRepository = eletrodomesticoRepository;
    }

    public List<Eletrodomestico> findAll() {
        return eletrodomesticoRepository.findAll();
    }

    public Eletrodomestico findById(Long id) {
        return eletrodomesticoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Eletrodomestico create(EletrodomesticoDTO eletrodomesticoDTO) {
        Eletrodomestico eletro = eletrodomesticoDTO.toEletrodomestico();
        return eletrodomesticoRepository.save(eletro);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Eletrodomestico> eletro = eletrodomesticoRepository.findById(id);
        eletrodomesticoRepository.delete(eletro.orElseThrow());
    }

    @Transactional
    public Eletrodomestico update(Long id, EletrodomesticoDTO eletroDTO) {
        Eletrodomestico eletro = findById(id);

        eletro.setNome(eletroDTO.getNome());
        eletro.setPotencia(eletroDTO.getPotencia());
        eletro.setModelo(eletroDTO.getModelo());
        eletro.setFabricacao(LocalDate.parse(eletroDTO.getFabricacao()));

        return eletrodomesticoRepository.save(eletro);
    }
}
