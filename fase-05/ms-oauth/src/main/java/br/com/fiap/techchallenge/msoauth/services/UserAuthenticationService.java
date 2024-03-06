package br.com.fiap.techchallenge.msoauth.services;

import br.com.fiap.techchallenge.msoauth.controllers.AuthRequest;
import br.com.fiap.techchallenge.msoauth.feignclients.UsuarioFeignClient;
import br.com.fiap.techchallenge.msoauth.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private final UsuarioFeignClient usuarioFeignClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenService tokenService;

    public String loginToken(AuthRequest authRequest) {
        Usuario usuario = usuarioFeignClient.findUsuarioByEmail(authRequest.email()).orElseThrow(
                () -> new UsernameNotFoundException("usuario_email não encontrado")
        );
        if (!passwordEncoder.matches(authRequest.password(), usuario.password())) {
            throw new BadCredentialsException("password incorreto");
        }
        return tokenService.generateToken(usuario);
    }
}
