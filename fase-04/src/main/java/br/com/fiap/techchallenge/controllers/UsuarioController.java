package br.com.fiap.techchallenge.controllers;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAll(Pageable pageable) {
        Page<Usuario> usuarioPage = usuarioService.findAll(pageable);
        return ResponseEntity.ok(usuarioPage.map(Usuario::toUsuarioDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable String id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario.toUsuarioDTO());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> insertUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioService.insert(usuarioDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(usuario.toUsuarioDTO());
    }
}
