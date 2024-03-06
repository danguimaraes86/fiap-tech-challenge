package br.com.fiap.techchallenge.usuarios.authorization.controllers;

import br.com.fiap.techchallenge.usuarios.authorization.services.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class UserAuthenticationController {

    private final UserAuthenticationService authenticationService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(new AuthResponse(
                authenticationService.loginToken(authRequest)
        ));
    }
}
