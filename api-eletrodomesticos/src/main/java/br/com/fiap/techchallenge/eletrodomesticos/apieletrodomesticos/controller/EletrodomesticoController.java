package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.controller;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.entity.Eletrodomestico;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.service.EletrodomesticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService service;

    @GetMapping
    public ResponseEntity<List<Eletrodomestico>> findAll() {
        List<Eletrodomestico> listEletro = service.findAll();
        return ResponseEntity.ok().body(listEletro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eletrodomestico> findById(@PathVariable Long id) {
        Eletrodomestico eletro = service.findById(id);
        return ResponseEntity.ok().body(eletro);
    }

    @PostMapping
    public ResponseEntity<EletrodomesticoDTO> createEletro(@RequestBody EletrodomesticoDTO eletroDTO, UriComponentsBuilder uriBuilder) {
        Eletrodomestico eletro = service.create(eletroDTO);
        URI uri = uriBuilder.path("/eletrodomestico/{id}").buildAndExpand(eletro.getId()).toUri();
        return ResponseEntity.created(uri).body(new EletrodomesticoDTO(eletro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eletrodomestico> updateEletro(@PathVariable Long id, @RequestBody EletrodomesticoDTO eletroDTO) {
        Eletrodomestico eletro = service.update(id, eletroDTO);
        return ResponseEntity.ok().body(eletro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEletro(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
