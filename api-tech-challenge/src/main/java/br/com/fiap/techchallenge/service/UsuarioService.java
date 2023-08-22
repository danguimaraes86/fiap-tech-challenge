package br.com.fiap.techchallenge.service;

import br.com.fiap.techchallenge.domain.dto.usuario.UsuarioDTO;
import br.com.fiap.techchallenge.domain.entidade.Usuario;
import br.com.fiap.techchallenge.infra.exceptions.ControllerNotFoundException;
import br.com.fiap.techchallenge.infra.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        try{
            Usuario usuario = usuarioRepository.getReferenceById(id);
            usuario.alterarSenha(senha);
        }catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Forneça um id válido diferente do id: " + id);
        }
    }
}
