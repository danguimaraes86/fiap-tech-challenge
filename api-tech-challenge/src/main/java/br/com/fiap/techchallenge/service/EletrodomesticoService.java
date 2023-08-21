package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.EletrodomesticoRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EletrodomesticoService {

    private final EletrodomesticoRepository eletrodomesticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsumidorRepository consumidorRepository;
    @Autowired
    public EletrodomesticoService(EletrodomesticoRepository eletrodomesticoRepository, UsuarioRepository usuarioRepository, ConsumidorRepository consumidorRepository) {
        this.eletrodomesticoRepository = eletrodomesticoRepository;
        this.usuarioRepository = usuarioRepository;
        this.consumidorRepository = consumidorRepository;
    }

    public List<Eletrodomestico> findAll() {
        return eletrodomesticoRepository.findAll();
    }

    public Eletrodomestico findById(Long id) {
        return eletrodomesticoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Eletrodomestico create(EletrodomesticoDTO eletrodomesticoDTO) {

        Usuario usuario = usuarioRepository.findById(Long.valueOf(eletrodomesticoDTO.usuarioId())).orElseThrow
                (() -> new RuntimeException("Usuário não encontrado"));
        LocalDate fabricacao = LocalDate.parse(eletrodomesticoDTO.fabricacao());
        Set<Consumidor> consumidores = eletrodomesticoDTO.consumidores().stream()
                .map(consumidorId -> consumidorRepository.findById(consumidorId.getId())
                        .orElseThrow(() -> new RuntimeException("Consumidor não encontrado com ID: " + consumidorId.getId())))
                .collect(Collectors.toSet());

        Eletrodomestico eletro = new Eletrodomestico(eletrodomesticoDTO.nome(), eletrodomesticoDTO.potencia(), eletrodomesticoDTO.modelo(),
                fabricacao, usuario, eletrodomesticoDTO.endereco(), consumidores);
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

        eletro.setNome(eletroDTO.nome());
        eletro.setPotencia(eletroDTO.potencia());
        eletro.setModelo(eletroDTO.modelo());
        eletro.setFabricacao(LocalDate.parse(eletroDTO.fabricacao()));

        return eletrodomesticoRepository.save(eletro);
    }
}
