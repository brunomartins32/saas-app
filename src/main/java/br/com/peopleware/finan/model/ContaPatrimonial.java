package br.com.peopleware.finan.model;

import br.com.peopleware.finan.model.enums.TipoContaPatrimonial;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContaPatrimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String instituicao;

    private BigDecimal saldoAtual;

    @Enumerated(EnumType.STRING)
    private TipoContaPatrimonial tipo;
}
