package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.infra.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Consumidor> findAll() {
        return pessoaRepository.findAll();
    }

    public Consumidor findById(Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Consumidor create(PessoaDto pessoaDto) {
        Consumidor consumidor = pessoaDto.toPessoa();
        return pessoaRepository.save(consumidor);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Consumidor> pessoa = pessoaRepository.findById(id);
        pessoaRepository.delete(pessoa.orElseThrow());
    }

    @Transactional
    public Consumidor update(Long id, PessoaDto pessoaDto) {
        Consumidor consumidorId = findById(id);

        consumidorId.setSexo(pessoaDto.getSexo());
        consumidorId.setNome(pessoaDto.getNome());
        consumidorId.setParentesco(pessoaDto.getParentesco());
        consumidorId.setDataNascimento(LocalDate.parse(pessoaDto.getDataNascimento()));
        return pessoaRepository.save(consumidorId);
    }
}
