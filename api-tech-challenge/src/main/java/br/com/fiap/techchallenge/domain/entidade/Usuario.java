package br.com.fiap.techchallenge.domain.entidade;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private Set<Consumidor> consumidores;

    @OneToMany(mappedBy = "usuario")
    private Set<Eletrodomestico> eletrodomesticos;

    @OneToMany(mappedBy = "usuario")
    private Set<Endereco> enderecos;

    //TODO: Mapeamento de Usuário com Endereço
    // private Endereco endereco;

    @Transient
    private final String emailRegex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Usuario(String email, String senha) {
        if (!email.matches(emailRegex)){
            throw new RuntimeException("E-mail não segue padrão");
        }
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
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
        return Objects.equals(id, usuario.id) && Objects.equals(email, usuario.email) && Objects.equals(senha, usuario.senha) && Objects.equals(consumidores, usuario.consumidores) && Objects.equals(eletrodomesticos, usuario.eletrodomesticos) && Objects.equals(enderecos, usuario.enderecos) && Objects.equals(emailRegex, usuario.emailRegex);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, email, senha, consumidores, eletrodomesticos, enderecos, emailRegex);
    }
    public Usuario(){
        //Necessário para o Hibernate
    }
}
