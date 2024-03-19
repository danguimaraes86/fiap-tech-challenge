package br.com.fiap.techchallenge.msoauth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${oauth.jwt.secret}")
    private String secret;

    public String generateToken(String usuarioEmail) {
        return JWT.create()
                .withIssuer("magazine-fiap")
                .withSubject(usuarioEmail)
                .withExpiresAt(genExpirationDate())
                .sign(Algorithm.HMAC256(secret));
    }

    private Instant genExpirationDate() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }
}
