package br.com.peopleware.finan.repository;

import br.com.peopleware.finan.model.ContaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContaFinanceiraRepository extends JpaRepository<ContaFinanceira, Long> {

    // Contas não efetivadas (a pagar/receber)
    @Query("SELECT c FROM ContaFinanceira c WHERE c.encerrada = false")
    List<ContaFinanceira> findContasAtivas();

    // Contas para dashboard (opcional)
    @Query("SELECT c FROM ContaFinanceira c WHERE c.efetivada = true")
    List<ContaFinanceira> findContasEfetivadas();

    List<ContaFinanceira> findByEfetivadaFalseAndEncerradaFalse();

    List<ContaFinanceira> findByEfetivadaTrue();

}