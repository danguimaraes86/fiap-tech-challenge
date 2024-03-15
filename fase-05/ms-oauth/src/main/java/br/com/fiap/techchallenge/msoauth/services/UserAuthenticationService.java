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

@RequiredArgsConstructor
@Service
public class UserAuthenticationService {

    private final UsuarioFeignClient usuarioFeignClient;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TokenService tokenService;
    private final CarrinhoFeignClient carrinhoFeignClient;

    public String loginToken(AuthRequest authRequest) {
        Usuario usuario = usuarioFeignClient.findUsuarioByEmail(authRequest.email()).orElseThrow(
                () -> new UsernameNotFoundException("usuario_email não encontrado")
        );
        if (!passwordEncoder.matches(authRequest.password(), usuario.password())) {
            throw new BadCredentialsException("password incorreto");
        }
        String carrinhoId = getNovoCarrinhoId(authRequest.email());
        return tokenService.generateToken(usuario.email(), carrinhoId);
    }

    private String getNovoCarrinhoId(String email) {
        return carrinhoFeignClient.createNovoCarrinho(email);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioFeignClient.createNovoUsuario(usuario);
    }
}
