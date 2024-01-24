package br.com.fiap.techchallenge.controller;

import br.com.fiap.techchallenge.domain.dto.usuario.AlteracaoSenhaDTO;
import br.com.fiap.techchallenge.domain.dto.usuario.UsuarioDTO;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO,
                                                       UriComponentsBuilder uriBuilder){
        Usuario usuario = usuarioService.criarUsuario(usuarioDTO);
        URI uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id,
                                             @RequestBody @Valid AlteracaoSenhaDTO alteracaoSenhaDTO){
        usuarioService.alterarSenha(id, alteracaoSenhaDTO.senha());
        return ResponseEntity.ok().build();
    }

}
