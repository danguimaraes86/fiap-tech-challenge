package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.ConsumidorDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsumidorService {

    private final ConsumidorRepository consumidorRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ConsumidorService(ConsumidorRepository consumidorRepository, UsuarioRepository usuarioRepository) {
        this.consumidorRepository = consumidorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Consumidor> findAll() {
        return consumidorRepository.findAll();
    }

    public Consumidor findById(Long id) {
        return consumidorRepository.findById(id).orElseThrow();
    }

    public Consumidor create(ConsumidorDTO consumidorDTO) {
        Long id = Long.valueOf(consumidorDTO.usuarioId());
        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            Set<Eletrodomestico> eletrodomesticos = null;

            if(consumidorDTO.eletrodomesticos() != null)
                consumidorDTO.eletrodomesticos().stream()
                        .map(eletrodomesticoId -> eletrodomesticoRepository.findById(eletrodomesticoId.getId())
                                .orElseThrow(() -> new RuntimeException("Consumidor não encontrado com ID: " + eletrodomesticoId.getId())))
                        .collect(Collectors.toSet());

            Consumidor consumidor = new Consumidor(consumidorDTO.nome(), LocalDate.parse(consumidorDTO.dataNascimento()),
                    consumidorDTO.sexo(), usuario, eletrodomesticos, consumidorDTO.parentesco());

            return consumidorRepository.save(consumidor);
        }catch (NoSuchElementException e){
            throw new ControllerNotFoundException("Usuário não encontrado com id: " + id);
        }
    }
    public Consumidor create(ConsumidorDTO consumidorDTO) {
        Usuario usuario = usuarioRepository.findById(Long.valueOf(consumidorDTO.usuarioId())).orElseThrow
                (() -> new RuntimeException("Usuário não encontrado"));
        Set<Eletrodomestico> eletrodomesticos = null;

        if(consumidorDTO.eletrodomesticos() != null)
            consumidorDTO.eletrodomesticos().stream()
                    .map(eletrodomesticoId -> eletrodomesticoRepository.findById(eletrodomesticoId.getId())
                            .orElseThrow(() -> new RuntimeException("Consumidor não encontrado com ID: " + eletrodomesticoId.getId())))
                    .collect(Collectors.toSet());

        Consumidor consumidor = new Consumidor(consumidorDTO.nome(), LocalDate.parse(consumidorDTO.dataNascimento()),
                consumidorDTO.sexo(), usuario, eletrodomesticos, consumidorDTO.parentesco());
        return consumidorRepository.save(consumidor);

    @Transactional
    public void delete(Long id) {
        Optional<Consumidor> pessoa = consumidorRepository.findById(id);
        consumidorRepository.delete(pessoa.orElseThrow());
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
        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Consumidor não encontrado com id: " + id);
        }
    }
}
