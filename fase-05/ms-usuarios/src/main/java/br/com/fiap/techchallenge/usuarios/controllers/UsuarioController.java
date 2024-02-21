package br.com.fiap.techchallenge.usuarios.controllers;

import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> findAllUsuarios(
            @SortDefault(sort = "nome") Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.findAllUsuarios(pageable);
        return ResponseEntity.ok(usuarios.map(Usuario::toUsuarioDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTO> findUsuarioById(@PathVariable String email) {
        Usuario usuario = usuarioService.findUsuarioByEmail(email);
        return ResponseEntity.ok(usuario.toUsuarioDTO());
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<UsuarioDTO>> findUsuarioByEmailOrNome(
            @SortDefault(sort = "nome") Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String nome) {
        Page<Usuario> usuarios = usuarioService.findUsuarioByEmailOrNome(pageable, email, nome);
        return ResponseEntity.ok(usuarios.map(Usuario::toUsuarioDTO));
    }
}
