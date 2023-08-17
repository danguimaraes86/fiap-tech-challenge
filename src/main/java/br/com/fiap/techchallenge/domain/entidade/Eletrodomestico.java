package br.com.fiap.techchallenge.domain.entidade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "eletrodomestico")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eletrodomestico that = (Eletrodomestico) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Eletrodomestico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", potencia='" + potencia + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricacao=" + fabricacao +
                '}';
    }
}
