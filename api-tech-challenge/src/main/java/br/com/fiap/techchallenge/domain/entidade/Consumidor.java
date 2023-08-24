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

    @ManyToMany(mappedBy = "consumidores", cascade = CascadeType.ALL)
    private Set<Eletrodomestico> eletrodomesticos;

    @Column(nullable = false)
    private String parentesco;

    public Consumidor(String nome, LocalDate dataNascimento, String sexo, Usuario usuario, Set<Eletrodomestico> eletrodomesticos, String parentesco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.usuario = usuario;
        this.eletrodomesticos = eletrodomesticos;
        this.parentesco = parentesco;
    }

    @Override
    public String toString() {
        return "Consumidor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", sexo='" + sexo + '\'' +
                ", usuario=" + usuario +
                ", eletrodomesticos=" + eletrodomesticos +
                ", parentesco='" + parentesco + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumidor that = (Consumidor) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(sexo, that.sexo) && Objects.equals(usuario, that.usuario) && Objects.equals(eletrodomesticos, that.eletrodomesticos) && Objects.equals(parentesco, that.parentesco);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataNascimento, sexo, usuario, eletrodomesticos, parentesco);
    }

}
