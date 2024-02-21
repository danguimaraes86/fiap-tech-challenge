package br.com.fiap.techchallenge.usuarios.models;

import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Usuario implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
