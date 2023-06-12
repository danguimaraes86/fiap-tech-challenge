package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.service;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.Eletrodomestico;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.repository.EletrodomesticoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EletrodomesticoService {

    @Autowired
    private EletrodomesticoRepository repository;

    public Eletrodomestico create(EletrodomesticoDTO eletrodomesticoDTO){
        Eletrodomestico eletro = eletrodomesticoDTO.toEletrodomestico();
        return repository.save(eletro);
    }

    public List<Eletrodomestico> findAll(){
        return repository.findAll();
    }

    public Eletrodomestico findById(Long id){
        Optional<Eletrodomestico> eletroOPT =  repository.findById(id);
        return eletroOPT.get();
    }

    @Transactional
    public Eletrodomestico update(Long id, EletrodomesticoDTO eletroDTO) {
        Eletrodomestico eletro = findById(id);
        eletro.setNome(eletroDTO.getNome());
        eletro.setPotencia(eletroDTO.getPotencia());
        eletro.setModelo(eletroDTO.getModelo());
        eletro.setFabricacao(LocalDate.parse(eletroDTO.getFabricacao()));

        return repository.save(eletro);
    }

    @Transactional
    public Boolean delete(Long id) {
        Optional<Eletrodomestico> eletro = repository.findById(id);
        if (eletro.isPresent()) {
            repository.delete(eletro.get());
            return true;
        }
        return false;
    }
}
