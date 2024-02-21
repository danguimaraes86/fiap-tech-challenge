package br.com.fiap.techchallenge.usuarios.security;

import br.com.fiap.techchallenge.usuarios.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Service
public class TokenService {
    private static final String ISSUER = "magazine-fiap";
    private final String secret = UUID.randomUUID().toString();
    private final Algorithm algorithm = Algorithm.HMAC256(secret);

    public String generateToken(Usuario usuario) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(usuario.getEmail())
                .withExpiresAt(genExpirationDate())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
