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

    public Endereco create(EnderecoDto enderecoDto){
        Endereco endereco = enderecoDto.toEndereco();
        return enderecoRepository.save(endereco);
    }

    public boolean delete(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()){
            enderecoRepository.delete(endereco.get());
            return true;
        }
        return false;
    }

    public List<Endereco> findAll(){
        return enderecoRepository.findAll();
    }

    public Endereco findById(Long id){
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.orElse(null);
    }

    public Endereco update(Long id, EnderecoDto enderecoDto){
        Endereco endereco = findById(id);
        endereco.setNomeInstalacao(enderecoDto.nomeInstalacao());
        endereco.setRua(enderecoDto.rua());
        endereco.setNumero(enderecoDto.numero());
        endereco.setBairro(enderecoDto.bairro());
        endereco.setCidade(enderecoDto.cidade());
        endereco.setEstado(enderecoDto.estado());
        return enderecoRepository.save(endereco);
    }
}
