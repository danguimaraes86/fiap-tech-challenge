package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.UsuarioDTO;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.email(), usuarioDTO.senha());
        return usuarioRepository.save(usuario);
    }
}
