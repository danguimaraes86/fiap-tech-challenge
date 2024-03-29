package br.com.fiap.techchallenge.usuarios.services;

import br.com.fiap.techchallenge.usuarios.exceptions.domain.UsuarioJaCadastradoException;
import br.com.fiap.techchallenge.usuarios.models.Role;
import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.repositories.UsuarioRespository;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UsuarioServiceTest {

    private static final Faker faker = new Faker(Locale.forLanguageTag("pt_BR"));
    AutoCloseable mocks;
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRespository usuarioRespository;

    private static Usuario getUsuarioMock() {
        return new Usuario(
                faker.internet().emailAddress(),
                faker.artist().name(),
                faker.internet().password(),
                Role.USER
        );
    }

    @BeforeEach
    void setup() {
        mocks = openMocks(this);
        usuarioService = new UsuarioService(usuarioRespository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Nested
    class BuscarUsuarios {

        @Test
        void deveBuscarUsuarios_RetornaPageableVazio() {
            Page<Usuario> usuarioPageFake = new PageImpl<>(Collections.emptyList());
            when(usuarioRespository.findAll(any(Pageable.class))).thenReturn(usuarioPageFake);

            Page<Usuario> usuarioPage = usuarioService.findUsuarioByEmailOrNome(
                    Pageable.unpaged(), "", "");
            verify(usuarioRespository, times(1)).findAll(Pageable.unpaged());

            assertThat(usuarioPage).isInstanceOf(Page.class);
            assertThat(usuarioPage.getContent()).isEmpty();
        }

        @Test
        void deveBuscarUsuarios_RetornaPageable() {
            Page<Usuario> usuarioPageFake = new PageImpl<>(List.of(
                    getUsuarioMock(),
                    getUsuarioMock(),
                    getUsuarioMock()
            ));
            when(usuarioRespository.findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(
                    any(Pageable.class), anyString(), anyString())).thenReturn(usuarioPageFake);

            Page<Usuario> usuarioPage = usuarioService.findUsuarioByEmailOrNome(
                    Pageable.unpaged(), "teste", "teste");
            verify(usuarioRespository, times(1))
                    .findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(
                            Pageable.unpaged(), "teste", "teste");

            assertThat(usuarioPage).isInstanceOf(Page.class);
            assertThat(usuarioPage.getTotalElements()).isEqualTo(usuarioPageFake.getTotalElements());
            assertThat(usuarioPage.getContent()).isEqualTo(usuarioPageFake.getContent());
        }

        @Test
        void deveBuscarUsuarioPorEmail_comSucesso() {
            Usuario usuarioMock = getUsuarioMock();
            String email = usuarioMock.getEmail();
            when(usuarioRespository.findById(anyString())).thenReturn(Optional.of(usuarioMock));

            Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(email);
            verify(usuarioRespository, times(1)).findById(email);

            assertThat(usuario).isNotEmpty().isEqualTo(Optional.of(usuarioMock));
            assertThat(usuario.get().getEmail()).isEqualTo(usuarioMock.getEmail());
            assertThat(usuario.get().getNome()).isEqualTo(usuarioMock.getNome());
            assertThat(usuario.get().getPassword()).isEqualTo(usuarioMock.getPassword());
            assertThat(usuario.get().getRole()).isEqualTo(Role.USER);
        }

        @Test
        void deveBuscarUsuarioPorEmail_optionalVazio() {
            String email = faker.internet().emailAddress();
            when(usuarioRespository.findById(anyString())).thenReturn(Optional.empty());

            Optional<Usuario> usuario = usuarioService.findUsuarioByEmail(email);
            verify(usuarioRespository, times(1)).findById(email);
            assertThat(usuario).isEmpty();
        }
    }

    @Nested
    class InserirUsuario {

        @Test
        void deveInserirUsuario_comSucesso() {
            Usuario usuarioMock = getUsuarioMock();
            UsuarioDTO usuarioDTO = usuarioMock.toUsuarioDTO();
            when(usuarioRespository.save(any(Usuario.class))).thenReturn(usuarioMock);

            Usuario usuario = usuarioService.createUsuario(usuarioDTO);
            verify(usuarioRespository, times(1)).save(any(Usuario.class));

            assertThat(usuario.getEmail()).isEqualTo(usuarioMock.getEmail());
            assertThat(usuario.getNome()).isEqualTo(usuarioMock.getNome());
            assertThat(usuario.getPassword()).isEqualTo(usuarioMock.getPassword());
            assertThat(usuario.getRole()).isEqualTo(usuarioMock.getRole());
        }
    }

    @Nested
    class Exceptions {

        @Test
        void deveLancarExcecao_inserirUsuario_usuarioJaCadastrado() {
            Usuario usuarioMock = getUsuarioMock();
            UsuarioDTO usuarioDTO = usuarioMock.toUsuarioDTO();
            when(usuarioRespository.findById(anyString())).thenReturn(Optional.of(usuarioMock));

            assertThatThrownBy(() -> usuarioService.createUsuario(usuarioDTO))
                    .isInstanceOf(UsuarioJaCadastradoException.class)
                    .hasMessage(String.format("usario_email %s já existe", usuarioDTO.email()));
            verify(usuarioRespository, times(1)).findById(anyString());
        }
    }
}
