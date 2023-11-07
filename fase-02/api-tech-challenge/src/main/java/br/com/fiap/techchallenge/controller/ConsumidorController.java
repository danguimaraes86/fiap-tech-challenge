package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.domain.dto.ConsumidorDTO;
import br.com.fiap.techchallenge.domain.entidade.Consumidor;
import br.com.fiap.techchallenge.service.ConsumidorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/consumidor")
public class ConsumidorController {

    @Autowired
    private ConsumidorService consumidorService;

    @GetMapping
    public ResponseEntity<List<ConsumidorDTO>> findAll(
            @RequestParam(required = false) HashMap<String, String> params) {

        if (params.isEmpty())
            return ResponseEntity.ok().body(
                    consumidorService.findAll().stream().map(ConsumidorDTO::new).toList());

        HashMap<String, String> busca = new HashMap<>(params);
        return ResponseEntity.ok().body(
                consumidorService.findByAtributo(busca).stream().map(ConsumidorDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumidorDTO> findById(@PathVariable Long id) {
        Consumidor consumidor = consumidorService.findById(id);
        return ResponseEntity.ok().body(new ConsumidorDTO(consumidor));
    }

    @PostMapping()
    public ResponseEntity<ConsumidorDTO> createConsumidor(@RequestBody @Valid ConsumidorDTO consumidorDTO,
            UriComponentsBuilder uriBuilder) {
        Consumidor consumidor = consumidorService.create(consumidorDTO);
        URI uri = uriBuilder.path("/consumidor/{id}").buildAndExpand(consumidor.getId()).toUri();
        return ResponseEntity.created(uri).body(new ConsumidorDTO(consumidor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumidorDTO> updateConsumidor(
            @PathVariable Long id,
            @RequestBody @Valid ConsumidorDTO consumidorDTO) {
        Consumidor consumidor = consumidorService.update(id, consumidorDTO);
        return ResponseEntity.ok().body(new ConsumidorDTO(consumidor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumidor(@PathVariable Long id) {
        consumidorService.delete(id);
        return ResponseEntity.ok().build();
    }
}
