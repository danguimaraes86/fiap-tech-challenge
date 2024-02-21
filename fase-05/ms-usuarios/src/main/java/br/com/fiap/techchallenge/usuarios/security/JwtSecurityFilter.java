package br.com.fiap.techchallenge.usuarios.security;

import br.com.fiap.techchallenge.usuarios.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        validateToken(header).ifPresent(userDetails -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
        filterChain.doFilter(request, response);
    }

    private Optional<UserDetails> validateToken(String header) {
        if (isEmpty(header) || !header.contains("Bearer")) {
            return Optional.empty();
        }
        String token = header.split(" ")[1].trim();
        return Optional.of(usuarioService.findUsuarioByEmail(tokenService.validateToken(token)));
    }
}
