package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.entities.Veiculo;
import br.com.fiap.techchallenge.entities.dtos.VeiculoDTO;
import br.com.fiap.techchallenge.services.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/veiculo")
@RestController
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> findAll() {
        List<Veiculo> veiculoList = veiculoService.findAll();
        return ResponseEntity.ok(veiculoList.stream().map(Veiculo::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable UUID id) {
        Veiculo veiculo = veiculoService.findById(id);
        return ResponseEntity.ok(veiculo.toDTO());
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> createVeiculo(
            @RequestHeader String condutorCpf,
            @RequestBody @Valid VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoService.create(veiculoDTO, condutorCpf);
        return ResponseEntity.ok(veiculo.toDTO());
    }

    @PutMapping("/{placa}")
    public ResponseEntity<VeiculoDTO> updateVeiculo(@PathVariable String placa, @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = veiculoService.update(placa, veiculoDTO);
        return ResponseEntity.ok(veiculo.toDTO());
    }

    @DeleteMapping("/{placa}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable String placa) {
        veiculoService.delete(placa);
        return ResponseEntity.noContent().build();
    }
}
