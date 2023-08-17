package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Consumidor>> findAll() {
        List<Consumidor> listConsumidors = pessoaService.findAll();
        return ResponseEntity.ok().body(listConsumidors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumidor> findById(@PathVariable Long id) {
        Consumidor consumidor = pessoaService.findById(id);
        return ResponseEntity.ok().body(consumidor);
    }

    @PostMapping
    public ResponseEntity<PessoaDto> createPessoa(@RequestBody @Valid PessoaDto pessoaDto, UriComponentsBuilder uriBuilder) {
        Consumidor consumidor = pessoaService.create(pessoaDto);
        URI uri = uriBuilder.path("/pessoa/{id}").buildAndExpand(consumidor.getId()).toUri();
        return ResponseEntity.created(uri).body(new PessoaDto(consumidor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumidor> updatePessoa(@PathVariable Long id, @RequestBody @Valid PessoaDto pessoaDto) {
        Consumidor consumidor = pessoaService.update(id, pessoaDto);
        return ResponseEntity.ok().body(consumidor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
