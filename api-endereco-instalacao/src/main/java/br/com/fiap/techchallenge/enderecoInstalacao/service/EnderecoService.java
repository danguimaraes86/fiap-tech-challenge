package br.com.fiap.techchallenge.enderecoInstalacao.service;

import br.com.fiap.techchallenge.enderecoInstalacao.infra.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }



}
