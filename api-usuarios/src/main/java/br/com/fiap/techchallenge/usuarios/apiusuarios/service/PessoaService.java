package br.com.fiap.techchallenge.usuarios.apiusuarios.service;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import br.com.fiap.techchallenge.usuarios.apiusuarios.infra.repository.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa create(PessoaDto pessoaDto){
        Pessoa pessoa = pessoaDto.toPessoa();
        return pessoaRepository.save(pessoa);
    }

    public Boolean delete(Long id){
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if(pessoa.isPresent()){
            pessoaRepository.delete(pessoa.get());
            return true;
        }
        return false;
    }

}
