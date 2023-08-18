package br.com.fiap.techchallenge.controller.exception;

public class ValidacaoCampo {

    private String campo;
    private String mensagem;

    public ValidacaoCampo(){

    }
    public ValidacaoCampo(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }
    public ValidacaoCampo setCampo(String campo) {
        this.campo = campo;
        return this;
    }

    public String getMensagem() {
        return mensagem;
    }
    public ValidacaoCampo setMensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }
}
