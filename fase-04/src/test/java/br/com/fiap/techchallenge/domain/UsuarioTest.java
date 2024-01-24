package br.com.fiap.techchallenge.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    void deveCriarObjetoCorretamente() {
        Usuario usuario = new Usuario();
        assertThat(usuario)
                .isInstanceOf(Usuario.class);
    }
}
