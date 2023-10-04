package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.entities.dtos.VeiculoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    private String marca;
    @NotNull
    private String modelo;
    @NotNull
    private String placa;

    public Veiculo(String marca, String modelo, String placa) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
    }

    public VeiculoDTO toDTO() {
        return new VeiculoDTO(this.id.toString(), this.marca, this.modelo, this.placa);
    }

    public void update(VeiculoDTO veiculoDTO) {
        if (veiculoDTO.marca() != null)
            this.marca = veiculoDTO.marca();
        if (veiculoDTO.modelo() != null)
            this.modelo = veiculoDTO.modelo();
        if (veiculoDTO.placa() != null)
            this.placa = veiculoDTO.placa();
    }
}
