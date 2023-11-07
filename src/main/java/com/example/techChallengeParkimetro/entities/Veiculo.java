package com.example.techChallengeParkimetro.entities;

import com.example.techChallengeParkimetro.entities.dtos.VeiculoDTO;
import jakarta.persistence.*;
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
    @Column(unique = true)
    private String placa;
    @NotNull
    private String condutorCPF;

    public Veiculo(String marca, String modelo, String placa, String condutorCPF) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.condutorCPF = condutorCPF;
    }

    public VeiculoDTO toDTO() {
        return new VeiculoDTO(this.marca, this.modelo, this.placa, this.condutorCPF);
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
