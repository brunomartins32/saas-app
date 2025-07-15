package br.com.peopleware.finan.dto;

import br.com.peopleware.finan.model.enums.TipoPessoa;
import lombok.Data;


@Data
public class ClienteDTO {
    private Long id;


    private String nome;


    private String documento;

    private TipoPessoa tipo;

    private boolean ativo;
}

