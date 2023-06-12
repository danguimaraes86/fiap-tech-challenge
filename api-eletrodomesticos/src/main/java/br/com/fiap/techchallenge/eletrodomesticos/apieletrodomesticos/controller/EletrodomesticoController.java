package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.controller;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.Eletrodomestico;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.service.EletrodomesticoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Eletrodomestico> listEletro = service.findAll();
        return ResponseEntity.ok().body(listEletro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Eletrodomestico eletro = service.findById(id);
        return eletro != null ? ResponseEntity.ok().body(eletro) :
                ResponseEntity.badRequest().build();
    }
    @PostMapping
    public ResponseEntity<?> createEletro(@RequestBody @Valid EletrodomesticoDTO eletroDTO, UriComponentsBuilder uriBuilder) {
        Eletrodomestico eletro = service.create(eletroDTO);
        URI uri = uriBuilder.path("/eletrodomestico/{id}").buildAndExpand(eletro.getId()).toUri();
        return ResponseEntity.created(uri).body(new EletrodomesticoDTO(eletro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEletro(@PathVariable Long id, @RequestBody @Valid EletrodomesticoDTO eletroDTO) {
        Eletrodomestico eletro = service.update(id, eletroDTO);
        return ResponseEntity.ok().body(eletro);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEletro(@PathVariable Long id) {
        return service.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }
}
