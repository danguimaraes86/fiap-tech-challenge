package br.com.fiap.techchallenge.enderecoInstalacao.domain.entidade;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table (name = "Enderecos")
@Getter
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nomeInstalacao;
    @Column(nullable = false)
    private String rua;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String complemento;

    public Endereco(){

    }
    public Endereco(String nomeInstalacao, String rua, String numero, String complemento){
        this.nomeInstalacao = nomeInstalacao;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }


}
