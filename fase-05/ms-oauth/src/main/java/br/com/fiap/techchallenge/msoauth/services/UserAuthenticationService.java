package br.com.fiap.techchallenge.msoauth.services;

import br.com.fiap.techchallenge.msoauth.controllers.AuthRequest;
import br.com.fiap.techchallenge.msoauth.feignclients.CarrinhoFeignClient;
import br.com.fiap.techchallenge.msoauth.feignclients.UsuarioFeignClient;
import br.com.fiap.techchallenge.msoauth.feignclients.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private static final String BEARER_TOKEN = "Bearer %s";

    private final UsuarioFeignClient usuarioFeignClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenService tokenService;
    private final CarrinhoFeignClient carrinhoFeignClient;

    public String loginToken(AuthRequest authRequest) {
        String token = format(BEARER_TOKEN, tokenService.generateToken("api_request"));
        Usuario usuario = usuarioFeignClient.findUsuarioByEmail(
                token, authRequest.email()).orElseThrow(
                () -> new UsernameNotFoundException("usuario_email não encontrado")
        );
        if (!passwordEncoder.matches(authRequest.password(), usuario.password())) {
            throw new BadCredentialsException("password incorreto");
        }
        String carrinhoId = getNovoCarrinhoId(authRequest.email());
        return tokenService.generateToken(carrinhoId);
    }

    private String getNovoCarrinhoId(String email) {
        String token = format(BEARER_TOKEN, tokenService.generateToken("api_request"));
        return carrinhoFeignClient.createNovoCarrinho(token, email);
    }

    public Usuario createUsuario(Usuario usuario) {
        String token = format(BEARER_TOKEN, tokenService.generateToken("api_request"));
        return usuarioFeignClient.createNovoUsuario(token, usuario);
    }
}
