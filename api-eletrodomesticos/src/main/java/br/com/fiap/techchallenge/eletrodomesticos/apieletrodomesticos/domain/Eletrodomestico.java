package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Eletrodomesticos")
public class Eletrodomestico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String potencia;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private LocalDate fabricacao;

    public Eletrodomestico(String nome, String potencia, String modelo, LocalDate fabricacao) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.fabricacao = fabricacao;
    }
}
