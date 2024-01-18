package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.exceptions.UsuarioNotFoundException;
import br.com.fiap.techchallenge.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario findById(String id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UsuarioNotFoundException("usuário não encontrado")
        );
    }

    public Usuario insert(UsuarioDTO usuarioDTO) {
        return usuarioRepository.insert(
                new Usuario(usuarioDTO.nome())
                        .adicionarFavorito(usuarioDTO.favoritos())
        );
    }

    public Usuario adicionarFavoritos(String id, List<ObjectId> favoritos) {
        return usuarioRepository.save(findById(id).adicionarFavorito(favoritos));
    }
}
