package br.com.fiap.techchallenge.utils;

public class StringSanitizer {

    public static String somenteNumeros(String string) {
        return string.replaceAll("[^0-9]", "");
    }
}
