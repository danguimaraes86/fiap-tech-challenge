package br.com.fiap.techchallenge.usuarios.services;

import br.com.fiap.techchallenge.usuarios.exceptions.UsuarioJaCadastradoException;
import br.com.fiap.techchallenge.usuarios.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.techchallenge.usuarios.models.Role;
import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.repositories.UsuarioRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Page<Usuario> findAllUsuarios(Pageable pageable) {
        return usuarioRespository.findAll(pageable);
    }

    public Usuario findUsuarioByEmail(String email) {
        return usuarioRespository.findById(email).orElseThrow(() ->
                new UsuarioNaoEncontradoException("usuario_email não encontrado")
        );
    }

    public Page<Usuario> findUsuarioByEmailOrNome(Pageable pageable, String email, String nome) {
        return usuarioRespository.findByEmailIgnoreCaseOrNomeLikeIgnoreCase(pageable, email, nome);
    }

    public Usuario createUsuario(UsuarioDTO usuarioDTO) {
        usuarioRespository.findById(usuarioDTO.email()).ifPresent(usuario -> {
            throw new UsuarioJaCadastradoException(
                    String.format("usario_email %s já existe", usuario.getEmail()));
        });
        String encodedPassword = passwordEncoder.encode(usuarioDTO.password());
        Usuario usuario = new Usuario(
                usuarioDTO.email(), usuarioDTO.nome(), encodedPassword, Role.USER);
        return usuarioRespository.save(usuario);
    }
}
