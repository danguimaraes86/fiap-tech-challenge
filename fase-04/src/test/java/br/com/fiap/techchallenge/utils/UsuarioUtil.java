package br.com.fiap.techchallenge.utils;

import br.com.fiap.techchallenge.domain.Usuario;
import br.com.fiap.techchallenge.domain.dtos.UsuarioDTO;
import com.github.javafaker.Faker;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class UsuarioUtil {

    public static Usuario gerarUsuarioMock() {
        Faker faker = new Faker(new Locale("pt_BR"));
        return new Usuario(
                ObjectId.get(),
                faker.witcher().witcher(),
                new ArrayList<>()
        );
    }

    public static List<ObjectId> gerarFavoritos() {
        return new ArrayList<>(Arrays.asList(
                ObjectId.get(),
                ObjectId.get(),
                ObjectId.get(),
                ObjectId.get()
        ));
    }

    public static UsuarioDTO gerarUsuarioDTOMock() {
        Faker faker = new Faker(new Locale("pt_BR"));
        return new UsuarioDTO(
                faker.witcher().witcher(),
                new ArrayList<>()
        );
    }
}
