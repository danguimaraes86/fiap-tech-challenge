package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.domain.dto.EletrodomesticoDTO;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.service.EletrodomesticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eletrodomestico")
public class EletrodomesticoController {

    @Autowired
    private EletrodomesticoService eletrodomesticoService;

    @GetMapping
    public ResponseEntity<List<Eletrodomestico>> findAll(
            @RequestParam(required = false) HashMap<String, String> params) {

        if (params.isEmpty()) return ResponseEntity.ok().body(eletrodomesticoService.findAll());

        HashMap<String, String> busca = new HashMap<>(params);
        List<Eletrodomestico> listEletro = eletrodomesticoService.findByAtributo(busca);
        return ResponseEntity.ok().body(listEletro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eletrodomestico> findById(@PathVariable Long id) {
        Eletrodomestico eletro = eletrodomesticoService.findById(id);
        return ResponseEntity.ok().body(eletro);
    }

    @PostMapping
    public ResponseEntity<EletrodomesticoDTO> createEletro(@RequestBody EletrodomesticoDTO eletroDTO, UriComponentsBuilder uriBuilder) {
        Eletrodomestico eletro = eletrodomesticoService.create(eletroDTO);
        URI uri = uriBuilder.path("/eletrodomestico/{id}").buildAndExpand(eletro.getId()).toUri();
        return ResponseEntity.created(uri).body(new EletrodomesticoDTO(eletro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eletrodomestico> updateEletro(@PathVariable Long id, @RequestBody EletrodomesticoDTO eletroDTO) {
        Eletrodomestico eletro = eletrodomesticoService.update(id, eletroDTO);
        return ResponseEntity.ok().body(eletro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEletro(@PathVariable Long id) {
        eletrodomesticoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
