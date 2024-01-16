package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
public class Usuario {

    @Id
    private String id;
    private String nome;
    private List<String> favoritos = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario adicionarFavorito(List<String> favoritos) {
        this.favoritos.addAll(favoritos);
        return this;
    }

    public UsuarioDTO toUsuarioDTO() {
        return new UsuarioDTO(this.nome, this.favoritos);
    }
}
