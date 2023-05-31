package br.com.fiap.techchallenge.usuarios.apiusuarios.controller;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import br.com.fiap.techchallenge.usuarios.apiusuarios.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity createPessoa(@RequestBody @Valid PessoaDto pessoaDto, UriComponentsBuilder uriBuilder){
        Pessoa pessoa = pessoaService.create(pessoaDto);
        URI uri = uriBuilder.path("/pessoa/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(new PessoaDto(pessoa));
    }
}
