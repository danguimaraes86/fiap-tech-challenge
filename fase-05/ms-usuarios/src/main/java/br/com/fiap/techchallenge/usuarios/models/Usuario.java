package br.com.fiap.techchallenge.usuarios.models;

import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Usuario {

    @Id
    @Column(unique = true)
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public UsuarioDTO toUsuarioDTO() {
        return new UsuarioDTO(this.email, this.nome, this.password, this.role.name());
    }
}
