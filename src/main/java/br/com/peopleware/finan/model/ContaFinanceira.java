package br.com.peopleware.finan.model;

import br.com.peopleware.finan.model.enums.TipoConta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "conta_financeira")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ContaFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo; // A PAGAR ou A RECEBER

    private BigDecimal valor;

    private LocalDate dataVencimento;

    private boolean quitada; // se a conta já foi paga/recebida
    private boolean efetivada; // novo campo para controlar efetivação
    private boolean encerrada = false;
}
