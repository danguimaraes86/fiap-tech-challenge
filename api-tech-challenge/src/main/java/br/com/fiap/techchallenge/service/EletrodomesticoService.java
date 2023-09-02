package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.exceptions.FormatacaoDateTimeException;
import br.com.fiap.techchallenge.infra.exceptions.RuntimeException;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.EletrodomesticoRepository;
import br.com.fiap.techchallenge.infra.repository.EnderecoRepository;
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
public class EletrodomesticoService {

    private final EletrodomesticoRepository eletrodomesticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsumidorRepository consumidorRepository;
    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EletrodomesticoService(EletrodomesticoRepository eletrodomesticoRepository, UsuarioRepository usuarioRepository, ConsumidorRepository consumidorRepository, EnderecoRepository enderecoRepository) {
        this.eletrodomesticoRepository = eletrodomesticoRepository;
        this.usuarioRepository = usuarioRepository;
        this.consumidorRepository = consumidorRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public List<Eletrodomestico> findAll() {
        return eletrodomesticoRepository.findAll();
    }


    public List<Eletrodomestico> findByAtributo(HashMap<String, String> busca) {
        try {
            LocalDate fabricacao = busca.containsKey("fabricacao") ? LocalDate.parse(busca.get("fabricacao")) : null;
            return eletrodomesticoRepository.findByNomeIgnoreCaseOrModeloIgnoreCaseOrFabricacaoAfter(
                    busca.get("nome"), busca.get("modelo"), fabricacao);
        } catch (DateTimeException e) {
            throw new FormatacaoDateTimeException("Utilize o formato AAAA-MM-DD");
        }
    }

    public Eletrodomestico findById(Long id) {
        return eletrodomesticoRepository.findById(id).orElseThrow(() -> new RuntimeException("Eletrodomestico não encontrado com ID: " + id));
    }

    @Transactional
    public Eletrodomestico create(EletrodomesticoDTO eletrodomesticoDTO, Long usuarioId, String enderecoId) {
        try {
            Long idEndereco = Long.parseLong(enderecoId);

            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow
                    (() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));

            Endereco endereco = enderecoRepository.findById(idEndereco).orElseThrow
                    (() -> new RuntimeException("Endereço não encontrado com ID: " + idEndereco));

            Set<Consumidor> consumidores = null;
            if (eletrodomesticoDTO.consumidores() != null)
                consumidores = eletrodomesticoDTO.consumidores().stream()
                        .map(consumidorId -> consumidorRepository.findById(consumidorId.getId())
                                .orElseThrow(() -> new RuntimeException("Consumidor não encontrado com ID: " + consumidorId.getId())))
                        .collect(Collectors.toSet());

            Eletrodomestico eletro = new Eletrodomestico(eletrodomesticoDTO.nome(), eletrodomesticoDTO.potencia(), eletrodomesticoDTO.modelo(),
                    LocalDate.parse(eletrodomesticoDTO.fabricacao()), usuario, endereco, consumidores);
            return eletrodomesticoRepository.save(eletro);
        } catch (NoSuchElementException e) {
            throw new ControllerNotFoundException("Não foi possivel completar a operação.");
        } catch (DateTimeException e) {
            throw new FormatacaoDateTimeException("Utilize o formato AAAA-MM-DD");
        }
    }

    @Transactional
    public Eletrodomestico update(Long id, EletrodomesticoDTO eletroDTO) {
        try {
            Eletrodomestico eletro = findById(id);

            eletro.setNome(eletroDTO.nome());
            eletro.setPotencia(eletroDTO.potencia());
            eletro.setModelo(eletroDTO.modelo());
            eletro.setFabricacao(LocalDate.parse(eletroDTO.fabricacao()));
            eletro.setEndereco(enderecoRepository.findById(Long.parseLong(eletroDTO.enderecoId())).get());
            eletro.setConsumidores(eletroDTO.consumidores());
            eletro.setUsuario(usuarioRepository.findById(Long.parseLong(eletroDTO.usuarioId())).get());

            return eletrodomesticoRepository.save(eletro);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Eletrodomestico não encontrado com id: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            Optional<Eletrodomestico> eletro = eletrodomesticoRepository.findById(id);
            eletrodomesticoRepository.delete(eletro.orElseThrow());
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Violação de Integridade da Base - ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de Integridade da Base");
        }
    }

}
