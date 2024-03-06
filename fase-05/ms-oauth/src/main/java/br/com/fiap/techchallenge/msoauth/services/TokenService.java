package br.com.fiap.techchallenge.msoauth.services;

import br.com.fiap.techchallenge.msoauth.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${oauth.jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("magazine-fiap")
                .withSubject(usuario.email())
                .withExpiresAt(genExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
