package br.com.fiap.techchallenge.carrinho.entities.enums;

public enum Status {

    AGUARDANDOPAGAMENTO("Aguardando Pagamento"),
    PAGAMENTOAPROVADO("Pagamento Aprovado"),
    SEPARACAO("Pedido em Separação"),
    TRANSPORTADORA("Aguardando Coleta pela Transportadora"),
    ENVIADO("Pedido Enviado"),
    ENTREGUE("Pedido entregue ao Destinatario");

    private final String mensagemDoStatus;

    Status(String mensagemDoStatus) {
        this.mensagemDoStatus = mensagemDoStatus;
    }

    public String getMensagemDoStatus() {
        return mensagemDoStatus;
    }
}
