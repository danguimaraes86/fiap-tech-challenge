package br.com.fiap.techchallenge.domain.entidade;

import lombok.Getter;
import lombok.Setter;

public class Usuario {

    @Getter @Setter
    private String email;

    public Usuario(String email) {
        this.email = email;
    }
}
