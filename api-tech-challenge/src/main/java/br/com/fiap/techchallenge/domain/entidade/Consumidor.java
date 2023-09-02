package br.com.fiap.techchallenge.domain.entidade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "consumidor")
public class Consumidor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private LocalDate dataNascimento;

        @Column(nullable = false)
        private String sexo;

        @ManyToOne
        @JoinColumn(name = "usuario_id")
        private Usuario usuario;

        @ManyToMany(mappedBy = "consumidores")
        private Set<Eletrodomestico> eletrodomesticos;

        @Column(nullable = false)
        private String parentesco;

    public Consumidor(String nome, LocalDate dataNascimento, String sexo, Usuario usuario, String parentesco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.usuario = usuario;
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return "Consumidor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", sexo='" + sexo + '\'' +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", parentesco='" + parentesco + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Consumidor that = (Consumidor) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
