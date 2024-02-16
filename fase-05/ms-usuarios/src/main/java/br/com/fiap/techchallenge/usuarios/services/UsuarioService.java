package br.com.fiap.techchallenge.usuarios.services;

import br.com.fiap.techchallenge.usuarios.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.techchallenge.usuarios.models.Usuario;
import br.com.fiap.techchallenge.usuarios.models.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.usuarios.repositories.UsuarioRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRespository usuarioRespository;

    public Page<Usuario> findAllUsuarios(Pageable pageable) {
        return usuarioRespository.findAll(pageable);
    }

    public Usuario findUsuarioById(String id) {
        return usuarioRespository.findById(id).orElseThrow(
                () -> new UsuarioNaoEncontradoException("usuario_id não encontrado")
        );
    }

    public Page<Usuario> findUsuarioByEmailOrNome(Pageable pageable, String email, String nome) {
        return usuarioRespository.findByEmailIgnoreCaseOrNomeLikeIgnoreCase(pageable, email, nome);
    }

    public Usuario insertUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.email(), usuarioDTO.nome(), usuarioDTO.password());
        return usuarioRespository.save(usuario);
    }
}
