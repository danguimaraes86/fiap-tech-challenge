package br.com.fiap.techchallenge.service;


import br.com.fiap.techchallenge.domain.dto.EnderecoDTO;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Endereco create(EnderecoDTO enderecoDto) {
        Endereco endereco = enderecoDto.toEndereco();
        return enderecoRepository.save(endereco);
    }

    public Endereco update(Long id, EnderecoDTO enderecoDto) {
        Endereco endereco = findById(id);

        endereco.setNomeInstalacao(enderecoDto.nomeInstalacao());
        endereco.setRua(enderecoDto.rua());
        endereco.setNumero(enderecoDto.numero());
        endereco.setBairro(enderecoDto.bairro());
        endereco.setCidade(enderecoDto.cidade());
        endereco.setEstado(enderecoDto.estado());
        return enderecoRepository.save(endereco);
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
