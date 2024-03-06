package br.com.fiap.techchallenge.usuarios.controllers;

import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAllUsuarios(
            @SortDefault(sort = "email") Pageable pageable,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nome) {
        Page<Usuario> usuarios = usuarioService.findUsuarioByEmailOrNome(pageable, email, nome);
        return ResponseEntity.ok(usuarios.map(Usuario::toUsuarioDTO));
    }

    @GetMapping("/busca")
    public ResponseEntity<Optional<Usuario>> findUsuarioById(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.findUsuarioByEmail(email));
    }

    @PostMapping("/novo")
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.createUsuario(usuarioDTO);
        return ResponseEntity.ok(usuario.toUsuarioDTO());
    }
}
