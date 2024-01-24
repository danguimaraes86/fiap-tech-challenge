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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToMany
    @JoinTable(
            name = "Consumidor_EletroDomestico",
            joinColumns = @JoinColumn(name = "eletrodomesticos_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "consumidores_id", referencedColumnName = "id"))
    private Set<Consumidor> consumidores;

    public Eletrodomestico(String nome, String potencia, String modelo, LocalDate fabricacao,
                           Usuario usuario, Endereco endereco, Set<Consumidor> consumidores) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.fabricacao = fabricacao;
        this.usuario = usuario;
        this.endereco = endereco;

        this.consumidores = consumidores;
    }

    public void removeConsumidor(Consumidor consumidor) {
        consumidores.remove(consumidor);
    }

    @Override
    public String toString() {
        return "Eletrodomestico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", potencia='" + potencia + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricacao=" + fabricacao +
                ", usuario=" + (usuario != null ? usuario.getId() : null) +
                ", endereco=" + (endereco != null ? endereco.getId() : null) +
                ", consumidores=" + (consumidores != null ? consumidores.size() : 0) +
                '}';
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Eletrodomestico that = (Eletrodomestico) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
