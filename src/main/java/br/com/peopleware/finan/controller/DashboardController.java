package br.com.peopleware.finan.controller;

import br.com.peopleware.finan.dto.DashboardDTO;
import br.com.peopleware.finan.model.enums.TipoConta;
import br.com.peopleware.finan.repository.ContaFinanceiraRepository;
import br.com.peopleware.finan.repository.ContaPatrimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    @Autowired
    private ContaFinanceiraRepository contaFinanceiraRepo;

    @Autowired
    private ContaPatrimonialRepository contaPatrimonialRepo;

    @GetMapping
    public DashboardDTO obterResumo() {
        BigDecimal totalPatrimonial = contaPatrimonialRepo.findAll().stream()
                .map(c -> c.getSaldoAtual())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReceber = contaFinanceiraRepo.findAll().stream()
                .filter(c -> c.getTipo().equals(TipoConta.A_RECEBER))
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPagar = contaFinanceiraRepo.findAll().stream()
                .filter(c -> c.getTipo().equals(TipoConta.A_PAGAR))
                .map(c -> c.getValor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardDTO(totalPatrimonial, totalReceber, totalPagar);
    }
}
