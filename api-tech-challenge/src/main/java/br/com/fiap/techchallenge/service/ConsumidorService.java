package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.ConsumidorDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.repository.ConsumidorRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        Usuario usuario = usuarioRepository.findById(Long.valueOf(consumidorDTO.usuarioId())).orElseThrow
                (() -> new RuntimeException("Usuário não encontrado"));

        Consumidor consumidor = new Consumidor(consumidorDTO.nome(), LocalDate.parse(consumidorDTO.dataNascimento()),
                consumidorDTO.sexo(), usuario, consumidorDTO.parentesco());
        return consumidorRepository.save(consumidor);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Consumidor> pessoa = consumidorRepository.findById(id);
        consumidorRepository.delete(pessoa.orElseThrow());
    }

    @Transactional
    public Consumidor update(Long id, ConsumidorDTO consumidorDTO) {
        Consumidor consumidorId = findById(id);

        consumidorId.setSexo(consumidorDTO.sexo());
        consumidorId.setNome(consumidorDTO.nome());
        consumidorId.setDataNascimento(LocalDate.parse(consumidorDTO.dataNascimento()));
        return consumidorRepository.save(consumidorId);
    }
}
