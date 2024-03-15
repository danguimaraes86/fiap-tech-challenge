package br.com.fiap.techchallenge.carrinho.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Status {

    AGUARDANDOPAGAMENTO("Aguardando Pagamento"),
    PAGAMENTOAPROVADO("Pagamento Aprovado"),
    SEPARACAO("Pedido em Separação"),
    TRANSPORTADORA("Aguardando Coleta pela Transportadora"),
    ENVIADO("Pedido Enviado"),
    ENTREGUE("Pedido entregue ao Destinatario");

    private final String mensagem;

}
