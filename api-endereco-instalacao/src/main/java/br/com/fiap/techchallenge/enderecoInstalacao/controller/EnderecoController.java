package br.com.fiap.techchallenge.enderecoInstalacao.controller;

import br.com.fiap.techchallenge.enderecoInstalacao.domain.dto.EnderecoDto;
import br.com.fiap.techchallenge.enderecoInstalacao.domain.entidade.Endereco;
import br.com.fiap.techchallenge.enderecoInstalacao.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity<?> findAll(){
        List<Endereco> listEndereco = enderecoService.findAll();

        return ResponseEntity.ok().body(listEndereco);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Endereco endereco = enderecoService.findById(id);

        return endereco != null ? ResponseEntity.ok(endereco) :
                ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<?> createEndereço(@RequestBody @Valid EnderecoDto enderecoDto, UriComponentsBuilder uriBuilder) {
        Endereco endereco = enderecoService.create(enderecoDto);
        URI uri = uriBuilder.path("endereco/{id}").buildAndExpand(endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(new EnderecoDto(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEndereco(@PathVariable Long id, @RequestBody @Valid EnderecoDto enderecoDto) {
        Endereco endereco = enderecoService.update(id, enderecoDto);

        return ResponseEntity.ok().body(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long id) {
        return enderecoService.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
