package br.com.fiap.techchallenge.usuarios.apiusuarios.domain.entidade;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;



@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private String parentesco;

    public Pessoa(String nome, LocalDate dataNascimento, String sexo, String parentesco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.parentesco = parentesco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", sexo='" + sexo + '\'' +
                ", parentesco='" + parentesco + '\'' +
                '}';
    }
}
