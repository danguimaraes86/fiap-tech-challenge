package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.ConsumidorDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.exceptions.FormatacaoDateTimeException;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.EletrodomesticoRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;
    private final UsuarioRepository usuarioRepository;
    private final EletrodomesticoRepository eletrodomesticoRepository;

    @Autowired
    public ConsumidorService(ConsumidorRepository consumidorRepository, UsuarioRepository usuarioRepository, EletrodomesticoRepository eletrodomesticoRepository) {
        this.consumidorRepository = consumidorRepository;
        this.usuarioRepository = usuarioRepository;
        this.eletrodomesticoRepository = eletrodomesticoRepository;
    }

    public List<Consumidor> findAll() {
        return consumidorRepository.findAll();
    }

    public Consumidor findById(Long id) {
        Consumidor consumidor = consumidorRepository
                .findById(id).orElseThrow(() -> new ControllerNotFoundException("Consumidor não encontrado com ID: " + id));
        System.out.println(consumidor);
        return consumidor;
    }

    public List<Consumidor> findByAtributo(HashMap<String, String> busca) {
        LocalDate dataNascimento = busca.containsKey("dataNascimento") ? LocalDate.parse(busca.get("dataNascimento")) : null;
        return consumidorRepository
                .findConsumidorByNomeIgnoreCaseOrSexoIgnoreCaseOrParentescoIgnoreCaseOrDataNascimentoBefore(
                        busca.get("nome"), busca.get("sexo"), busca.get("parentesco"), dataNascimento
                );

    }

    @Transactional
    public Consumidor create(ConsumidorDTO consumidorDTO) {
        Long usuarioId = consumidorDTO.usuarioId();
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(
                    () -> new ControllerNotFoundException("Usuário não encontrado com ID: " + usuarioId));

            Set<Eletrodomestico> eletrodomesticos = null;

            if (consumidorDTO.eletrodomesticosIds() != null){
                eletrodomesticos = consumidorDTO.eletrodomesticosIds().stream()
                        .map(id -> eletrodomesticoRepository.findById(id)
                                .orElseThrow(() -> new ControllerNotFoundException("Eletrodomestico não encontrado com ID: " + id)))
                        .collect(Collectors.toSet());
            }

            LocalDate dataNascimento = LocalDate.parse(consumidorDTO.dataNascimento());
            Consumidor consumidor = new Consumidor(consumidorDTO.nome(), dataNascimento,
                    consumidorDTO.sexo(), usuario, consumidorDTO.parentesco());

            for (Eletrodomestico eletrodomestico : eletrodomesticos){
                eletrodomestico.getConsumidores().add(consumidor);
            }
            consumidor.setEletrodomesticos(eletrodomesticos);

            return consumidorRepository.save(consumidor);

        } catch (NoSuchElementException e) {
            throw new ControllerNotFoundException("Usuário não encontrado com id: " + usuarioId);
        } catch (DateTimeException e) {
            throw new FormatacaoDateTimeException("Utilize o formato AAAA-MM-DD");
        }
    }

    @Transactional
    public Consumidor update(Long id, ConsumidorDTO consumidorDTO) {
        try {
            Consumidor consumidor = consumidorRepository.getReferenceById(id);
            consumidor.setSexo(consumidorDTO.sexo());
            consumidor.setNome(consumidorDTO.nome());
            consumidor.setDataNascimento(LocalDate.parse(consumidorDTO.dataNascimento()));
            consumidor.setParentesco(consumidorDTO.parentesco());

            return consumidorRepository.save(consumidor);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Consumidor não encontrado com id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Optional<Consumidor> optionalConsumidor = consumidorRepository.findById(id);

            if (optionalConsumidor.isPresent()) {
                Consumidor consumidor = optionalConsumidor.get();

                consumidor.getEletrodomesticos().forEach(eletrodomestico -> eletrodomestico.removeConsumidor(consumidor));

                consumidorRepository.saveAndFlush(consumidor);

                consumidorRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Consumidor não encontrado com ID: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Erro ao excluir consumidor: " + e.getMessage());
        }
    }
}
