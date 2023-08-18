package br.com.fiap.techchallenge.infra.repository;

import br.com.fiap.techchallenge.domain.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
