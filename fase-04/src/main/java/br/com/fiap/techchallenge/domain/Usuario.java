package br.com.fiap.techchallenge.domain;

import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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
    private ObjectId id;
    private String nome;
    private List<ObjectId> favoritos = new ArrayList<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario adicionarFavorito(List<ObjectId> favoritos) {
        this.favoritos.addAll(favoritos);
        return this;
    }

    public UsuarioDTO toUsuarioDTO() {
        List<String> favoritosList = this.favoritos
                .stream().map(ObjectId::toHexString).toList();
        return new UsuarioDTO(this.nome, favoritosList);
    }
}
