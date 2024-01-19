package br.com.fiap.techchallenge.services;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import br.com.fiap.techchallenge.exceptions.FavoritoNaoEncontradoException;
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
    private final VideoService videoService;


    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario findById(ObjectId id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new UsuarioNotFoundException(
                        String.format("usuario_id %s não encontrado", id)
                )
        );
    }

    public Usuario insert(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.insert(new Usuario(usuarioDTO.nome()));
        if (usuarioDTO.favoritos() != null) {
            usuario.adicionarFavorito(validarVideos(usuarioDTO.favoritos()));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario adicionarFavoritos(ObjectId id, List<String> favoritosList) {
        Usuario usuario = findById(id);
        return usuarioRepository.save(
                usuario.adicionarFavorito(validarVideos(favoritosList))
        );
    }

    private List<ObjectId> validarVideos(List<String> favoritos) {
        favoritos.forEach(this::validarObjectId);
        List<ObjectId> videosList = favoritos.stream().map(ObjectId::new).toList();
        videosList.forEach(videoService::findById);
        return videosList;
    }


    private void validarObjectId(String favorito) {
        if (!ObjectId.isValid(favorito)) {
            throw new FavoritoNaoEncontradoException(
                    String.format("video_id %s com formato incorreto", favorito)
            );
        }
    }
}
