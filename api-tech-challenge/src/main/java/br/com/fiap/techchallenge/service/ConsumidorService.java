package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.ConsumidorDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.exceptions.FormatacaoDateTimeException;
import br.com.fiap.techchallenge.infra.exceptions.RuntimeException;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.EletrodomesticoRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return consumidorRepository.findById(id).orElseThrow(() -> new RuntimeException("Consumidor não encontrado com ID: " + id));
    }

    public List<Consumidor> findByAtributo(HashMap<String, String> busca) {
        LocalDate dataNascimento = busca.containsKey("dataNascimento") ? LocalDate.parse(busca.get("dataNascimento")) : null;
        return consumidorRepository
                .findConsumidorByNomeIgnoreCaseOrSexoIgnoreCaseOrParentescoIgnoreCaseOrDataNascimentoBefore(
                        busca.get("nome"), busca.get("sexo"), busca.get("parentesco"), dataNascimento
                );

    }

    public Consumidor create(ConsumidorDTO consumidorDTO, Long usuarioId) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(
                    () -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));

            Set<Eletrodomestico> eletrodomesticos = null;

            if (consumidorDTO.eletrodomesticos() != null)
                eletrodomesticos = consumidorDTO.eletrodomesticos().stream()
                        .map(eletrodomesticoId -> eletrodomesticoRepository.findById(eletrodomesticoId.getId())
                                .orElseThrow(() -> new RuntimeException("Eletrodomestico não encontrado com ID: " + eletrodomesticoId.getId())))
                        .collect(Collectors.toSet());

            LocalDate dataNascimento = LocalDate.parse(consumidorDTO.dataNascimento());
            Consumidor consumidor = new Consumidor(consumidorDTO.nome(), dataNascimento,
                    consumidorDTO.sexo(), usuario, eletrodomesticos, consumidorDTO.parentesco());

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
            consumidor.setEletrodomesticos(consumidorDTO.eletrodomesticos());
            consumidor.setParentesco(consumidorDTO.parentesco());

            return consumidorRepository.save(consumidor);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Consumidor não encontrado com id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Optional<Consumidor> pessoa = consumidorRepository.findById(id);

            consumidorRepository.delete(pessoa.orElseThrow());

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Violação de Integridade da Base - ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de Integridade da Base");
        }
    }
}
