package br.com.fiap.techchallenge.enderecoInstalacao.service;

import br.com.fiap.techchallenge.enderecoInstalacao.domain.dto.EnderecoDto;
import br.com.fiap.techchallenge.enderecoInstalacao.domain.entidade.Endereco;
import br.com.fiap.techchallenge.enderecoInstalacao.infra.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }
    @Transactional
    public Endereco create(EnderecoDto enderecoDto){
        Endereco endereco = enderecoDto.toEndereco();
        return enderecoRepository.save(endereco);
    }
    @Transactional
    public boolean delete(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()){
            enderecoRepository.delete(endereco.get());
            return true;
        }
        return false;
    }

    @Transactional
    public List<Endereco> findAll(){
        return enderecoRepository.findAll();
    }

    @Transactional
    public Endereco findById(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElse(null);
    }

    @Transactional
    public Endereco update(Long id, EnderecoDto enderecoDto){
        Endereco enderecoId = findById(id);
        enderecoId.setNomeInstalacao(enderecoDto.getNomeInstalacao());
        enderecoId.setRua(enderecoDto.getRua());
        enderecoId.setNumero(enderecoDto.getNumero());
        enderecoId.setComplemento(enderecoDto.getComplemento());
        
        return enderecoRepository.save(enderecoId);
    }
}
