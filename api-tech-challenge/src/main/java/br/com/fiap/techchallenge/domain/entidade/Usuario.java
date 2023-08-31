package br.com.fiap.techchallenge.domain.entidade;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private Set<Consumidor> consumidores;

    @OneToMany(mappedBy = "usuario")
    private Set<Eletrodomestico> eletrodomesticos;

    @OneToMany(mappedBy = "usuario")
    private Set<Endereco> enderecos;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public void alterarSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", consumidores=" + consumidores +
                ", eletrodomesticos=" + eletrodomesticos +
                ", enderecos=" + enderecos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id)
                && Objects.equals(email, usuario.email)
                && Objects.equals(senha, usuario.senha)
                && Objects.equals(consumidores, usuario.consumidores)
                && Objects.equals(eletrodomesticos, usuario.eletrodomesticos)
                && Objects.equals(enderecos, usuario.enderecos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, consumidores, eletrodomesticos, enderecos);
    }

}
