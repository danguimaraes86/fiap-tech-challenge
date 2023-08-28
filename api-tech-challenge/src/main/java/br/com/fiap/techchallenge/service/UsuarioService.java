package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.usuario.UsuarioDTO;
import br.com.fiap.techchallenge.domain.entidade.Eletrodomestico;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.exceptions.DatabaseException;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*TODO: Fazer com que a senha seja criptografada quando salva no banco de dados
            Criar um regex para que a senha siga um certo padrão e fazer estas validações
     */

    @Transactional
    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.email(), usuarioDTO.senha());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, String senha) {
        try {
            Usuario usuario = usuarioRepository.getReferenceById(id);
            usuario.alterarSenha(senha);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Usuario id: " + id + "não encontrado");
        }
    }

    public void delete(Long id) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(id);

            usuarioRepository.delete(usuario.orElseThrow());

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Violação de Integridade da Base - ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de Integridade da Base");
        }
    }
}
