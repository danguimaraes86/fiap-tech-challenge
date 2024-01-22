package br.com.fiap.techchallenge;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categoria {

    ACAO("1", "AÇÃO"),
    DRAMA("2", "DRAMA"),
    AVENTURA("3", "AVENTURA"),
    FANTASIA("4", "FANTASIA"),
    COMEDIA("5", "COMÉDIA"),
    SUSPENSE("6", "SUSPENSE"),
    ROMANCE("7", "ROMANCE"),
    DOCUMENTARIO("8", "DOCUMENTARIO");

    private String code;
    private String genero;

    public static Categoria getEnum(String code) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.getCode().equalsIgnoreCase(code)) {
                return categoria;
            }
        }
        return null;
    }
}
