package br.com.fiap.techchallenge.usuarios.models;

import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
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
//  [TODO] incluir @Email
    private String email;
    @NotBlank
    private String nome;
    @NotBlank
    @Min(value = 4)
    private String password;

    public UsuarioDTO toUsuarioDTO() {
        return new UsuarioDTO(this.email, this.nome, this.password);
    }
}
