package br.com.fiap.techchallenge.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;

import java.util.List;

public record UsuarioDTO(
        @NotBlank
        String nome,
        List<ObjectId> favoritos
) {
}
