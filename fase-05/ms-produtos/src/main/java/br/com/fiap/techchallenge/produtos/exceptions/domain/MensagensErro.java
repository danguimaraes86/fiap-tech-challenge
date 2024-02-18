package br.com.fiap.techchallenge.produtos.exceptions.domain;

import lombok.Getter;

@Getter
public enum MensagensErro {
    PRODUTO_NAO_ENCONTRADO("produto_id %s não encontrado"),
    NOME_JA_CADASTRADO("produto_nome %s já cadastrado"),
    PRECO_ABAIXO_ZERO("produto_preco não pode ser menor que R$ 0,00"),
    ESTOQUE_INSUFICIENTE("o pedido atual é de %d. quantidade atual é %d.");

    private final String mensagem;

    MensagensErro(String mensagem) {
        this.mensagem = mensagem;
    }
}
