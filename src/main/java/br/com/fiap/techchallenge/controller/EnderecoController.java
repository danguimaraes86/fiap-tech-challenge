package br.com.fiap.techchallenge.controller;


import br.com.fiap.techchallenge.domain.dto.EnderecoDto;
import br.com.fiap.techchallenge.domain.dto.PayloadDTO;
import br.com.fiap.techchallenge.domain.dto.UsuarioDTO;
import br.com.fiap.techchallenge.domain.entidade.Endereco;
import br.com.fiap.techchallenge.service.EnderecoService;
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
    public ResponseEntity<List<Endereco>> findAll() {
        List<Endereco> listEndereco = enderecoService.findAll();
        return ResponseEntity.ok().body(listEndereco);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> findById(@PathVariable Long id) {
        Endereco endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity<EnderecoDto> createEndereco(
            @RequestBody PayloadDTO<EnderecoDto> payloadDTO,
            UriComponentsBuilder uriBuilder) {
        EnderecoDto enderecoDto = payloadDTO.getData();
        UsuarioDTO usuarioDTO = payloadDTO.getUsuario();
        Endereco endereco = enderecoService.create(enderecoDto);
        // [TODO] vincular usuario
        URI uri = uriBuilder.path("endereco/{id}").buildAndExpand(endereco.getId()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoDto(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Long id, @RequestBody @Valid EnderecoDto enderecoDto) {
        Endereco endereco = enderecoService.update(id, enderecoDto);
        return ResponseEntity.ok().body(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
