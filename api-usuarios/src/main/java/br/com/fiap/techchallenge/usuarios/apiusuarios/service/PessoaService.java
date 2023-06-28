package br.com.fiap.techchallenge.usuarios.apiusuarios.service;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import br.com.fiap.techchallenge.usuarios.apiusuarios.infra.repository.PessoaRepository;
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

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Pessoa create(PessoaDto pessoaDto) {
        Pessoa pessoa = pessoaDto.toPessoa();
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        pessoaRepository.delete(pessoa.orElseThrow());
    }

    @Transactional
    public Pessoa update(Long id, PessoaDto pessoaDto) {
        Pessoa pessoaId = findById(id);

        pessoaId.setSexo(pessoaDto.getSexo());
        pessoaId.setNome(pessoaDto.getNome());
        pessoaId.setParentesco(pessoaDto.getParentesco());
        pessoaId.setDataNascimento(LocalDate.parse(pessoaDto.getDataNascimento()));

        return pessoaRepository.save(pessoaId);
    }
}
