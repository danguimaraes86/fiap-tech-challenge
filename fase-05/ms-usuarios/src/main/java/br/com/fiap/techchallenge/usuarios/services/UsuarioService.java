package br.com.fiap.techchallenge.usuarios.services;

import br.com.fiap.techchallenge.usuarios.exceptions.domain.UsuarioJaCadastradoException;
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

import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRespository.findById(email);
    }

    public Page<Usuario> findUsuarioByEmailOrNome(Pageable pageable, String email, String nome) {
        if (isEmpty(email) && isEmpty(nome)) {
            return usuarioRespository.findAll(pageable);
        }
        return usuarioRespository.findByEmailContainingIgnoreCaseOrNomeContainingIgnoreCase(pageable, email, nome);
    }

    public Usuario createUsuario(UsuarioDTO usuarioDTO) {
        usuarioRespository.findById(usuarioDTO.email()).ifPresent(usuario -> {
            throw new UsuarioJaCadastradoException(
                    String.format("usario_email %s já existe", usuario.getEmail()));
        });
        String encodedPassword = passwordEncoder.encode(usuarioDTO.password());
        Role role = isEmpty(usuarioDTO.role()) ?
                Role.USER : Role.valueOf(usuarioDTO.role().toUpperCase());
        Usuario usuario = new Usuario(
                usuarioDTO.email(), usuarioDTO.nome(), encodedPassword, role);
        return usuarioRespository.save(usuario);
    }
}
