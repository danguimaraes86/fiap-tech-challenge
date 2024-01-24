package br.com.fiap.techchallenge.controller;


import br.com.fiap.techchallenge.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.domain.dto.EnderecoDTO;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity<List<EnderecoDTO>> findAll(
            @RequestParam(required = false) HashMap<String, String> params) {

        if (params.isEmpty())
            return ResponseEntity.ok().body(
                    enderecoService.findAll().stream().map(EnderecoDTO::new).toList());

        HashMap<String, String> busca = new HashMap<>(params);
        return ResponseEntity.ok().body(
                enderecoService.findByAtributo(busca).stream().map(EnderecoDTO::new).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id) {
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(new EnderecoDTO(endereco));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDto,
                                                        UriComponentsBuilder uriBuilder) {
        Endereco endereco = enderecoService.create(enderecoDto);

        URI uri = uriBuilder.path("endereco/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoDTO(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Long id, @RequestBody @Valid EnderecoDTO enderecoDto) {
        Endereco endereco = enderecoService.update(id, enderecoDto);
        return ResponseEntity.ok().body(new EnderecoDTO(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
