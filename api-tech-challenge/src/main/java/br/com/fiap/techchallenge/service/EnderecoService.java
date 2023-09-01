package br.com.fiap.techchallenge.service;


import br.com.fiap.techchallenge.domain.dto.EnderecoDTO;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.repository.EnderecoRepository;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public List<Endereco> findByAtributo(HashMap<String, String> busca) {
        return enderecoRepository.findByNomeInstalacaoIgnoreCaseOrEstadoIgnoreCaseOrCidadeIgnoreCaseOrBairroIgnoreCase(
                busca.get("nomeInstalacao"), busca.get("estado"), busca.get("cidade"), busca.get("bairro")
        );
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new RuntimeException("Endereço não encontrado com ID: " + id));
    }

    @Transactional
    public Endereco create(EnderecoDTO enderecoDto, Long usuarioId) {
        try {
            Endereco endereco = enderecoDto.toEndereco();
            endereco.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
            return enderecoRepository.save(endereco);
        } catch (NoSuchElementException e) {
            throw new ControllerNotFoundException("Não foi possivel completar a operação.");
        }
    }

    public Endereco update(Long id, EnderecoDTO enderecoDto) {
        try {
            Endereco endereco = findById(id);

            endereco.setNomeInstalacao(enderecoDto.nomeInstalacao());
            endereco.setRua(enderecoDto.rua());
            endereco.setNumero(enderecoDto.numero());
            endereco.setBairro(enderecoDto.bairro());
            endereco.setCidade(enderecoDto.cidade());
            endereco.setEstado(enderecoDto.estado());

            return enderecoRepository.save(endereco);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Endereço não encontrado com id: " + id);
        }
    }

    public void delete(Long id) {
        try {
            Optional<Endereco> endereco = enderecoRepository.findById(id);

            enderecoRepository.delete(endereco.orElseThrow());

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Violação de Integridade da Base - ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de Integridade da Base");
        }
    }
}
