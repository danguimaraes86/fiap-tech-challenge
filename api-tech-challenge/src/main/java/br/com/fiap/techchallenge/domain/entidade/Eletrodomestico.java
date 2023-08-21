package br.com.fiap.techchallenge.domain.entidade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToMany(mappedBy = "eletrodomesticos")
    private Set<Consumidor> consumidores;

    public Eletrodomestico(String nome, String potencia, String modelo, LocalDate fabricacao, Usuario usuario, Endereco endereco, Set<Consumidor> consumidores) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.fabricacao = fabricacao;
        this.usuario = usuario;
        this.endereco = endereco;

        this.consumidores = consumidores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Eletrodomestico that = (Eletrodomestico) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(potencia, that.potencia) && Objects.equals(modelo, that.modelo) && Objects.equals(fabricacao, that.fabricacao) && Objects.equals(usuario, that.usuario) && Objects.equals(endereco, that.endereco) && Objects.equals(consumidores, that.consumidores);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, nome, potencia, modelo, fabricacao, usuario, endereco, consumidores);
    }
    @Override
    public String toString() {
        return "Eletrodomestico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", potencia='" + potencia + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricacao=" + fabricacao +
                ", usuario=" + usuario +
                ", endereco=" + endereco +
                ", consumidores=" + consumidores +
                '}';
    }
}
