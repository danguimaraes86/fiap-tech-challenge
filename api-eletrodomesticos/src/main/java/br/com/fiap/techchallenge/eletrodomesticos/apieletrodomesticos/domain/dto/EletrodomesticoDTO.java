package br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.dto;

import br.com.fiap.techchallenge.eletrodomesticos.apieletrodomesticos.domain.Eletrodomestico;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NotBlank
public class EletrodomesticoDTO {

    private String nome;
    private String potencia;
    private String modelo;
    private String fabricacao;

    public EletrodomesticoDTO(Eletrodomestico eletrodomesticos) {
        this(eletrodomesticos.getNome(), eletrodomesticos.getPotencia(), eletrodomesticos.getModelo(), eletrodomesticos.getFabricacao().toString());
    }

    public Eletrodomestico toEletrodomestico() throws DateTimeParseException {
        LocalDate fabricacao = LocalDate.parse(this.fabricacao);
        return new Eletrodomestico(this.nome, this.potencia, this.modelo, fabricacao);
    }

}
