package br.com.fiap.techchallenge.usuarios.authorization.services;

import br.com.fiap.techchallenge.usuarios.authorization.controllers.AuthRequest;
import br.com.fiap.techchallenge.usuarios.exceptions.domain.UsuarioNaoEncontradoException;
import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public String loginToken(AuthRequest authRequest) {
        Usuario usuario = usuarioService.findUsuarioByEmail(authRequest.email()).orElseThrow(
                () -> new UsuarioNaoEncontradoException("usuario_email não encontrado"));

        if (!passwordEncoder.matches(authRequest.password(), usuario.getPassword())) {
            throw new BadCredentialsException("password incorreto");
        }
        return tokenService.generateToken(usuario);
    }
}
