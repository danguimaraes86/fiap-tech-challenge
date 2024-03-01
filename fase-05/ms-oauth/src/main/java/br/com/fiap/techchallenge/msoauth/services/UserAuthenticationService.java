package br.com.fiap.techchallenge.msoauth.services;

import br.com.fiap.techchallenge.msoauth.controllers.AuthRequest;
import br.com.fiap.techchallenge.msoauth.feignclients.UsuarioDTO;
import br.com.fiap.techchallenge.msoauth.feignclients.UsuarioFeignClient;
import br.com.fiap.techchallenge.msoauth.models.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class UserAuthenticationService implements UserDetailsService {

    private final UsuarioFeignClient usuarioFeignClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioDTO usuario = usuarioFeignClient.findUsuarioByEmail(email).getBody();
        if (isEmpty(usuario)) {
            throw new UsernameNotFoundException("usuario_email não encontrado");
        }
        return new UserAuthentication(usuario.email(), usuario.password(), usuario.role());
    }

    public String loginToken(AuthRequest authRequest) {
        UserDetails usuario = loadUserByUsername(authRequest.email());
        if (!passwordEncoder.matches(authRequest.password(), usuario.getPassword())) {
            throw new BadCredentialsException("password incorreto");
        }
        return tokenService.generateToken(usuario);
    }
}
