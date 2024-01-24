package br.com.fiap.techchallenge.usuarios.apiusuarios.controller;

import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.dto.PessoaDto;
import br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade.Pessoa;
import br.com.fiap.techchallenge.usuarios.apiusuarios.service.PessoaService;
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
    public ResponseEntity<List<Pessoa>> findAll() {
        List<Pessoa> listPessoas = pessoaService.findAll();
        return ResponseEntity.ok().body(listPessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.findById(id);
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping
    public ResponseEntity<PessoaDto> createPessoa(@RequestBody @Valid PessoaDto pessoaDto, UriComponentsBuilder uriBuilder) {
        Pessoa pessoa = pessoaService.create(pessoaDto);
        URI uri = uriBuilder.path("/pessoa/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(new PessoaDto(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @RequestBody @Valid PessoaDto pessoaDto) {
        Pessoa pessoa = pessoaService.update(id, pessoaDto);
        return ResponseEntity.ok().body(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
