package br.com.fiap.techchallenge.usuarios.auth;

import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.security.TokenService;
import br.com.fiap.techchallenge.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.createUsuario(usuarioDTO);
        return ResponseEntity.ok(usuario.toUsuarioDTO());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUsuario(
            @RequestBody @Valid AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(), authRequest.password()
                ));
        String token = tokenService.generateToken(usuarioService.findUsuarioByEmail(authRequest.email()));
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
