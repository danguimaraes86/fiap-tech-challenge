package br.com.fiap.techchallenge.usuarios.repositories;

import br.com.fiap.techchallenge.usuarios.models.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRespository extends JpaRepository<Usuario, String> {
    Page<Usuario> findByEmailIgnoreCaseOrNomeLikeIgnoreCase(Pageable pageable, String email, String nome);
}
