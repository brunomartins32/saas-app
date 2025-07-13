package br.com.peopleware.finan.repository;

import br.com.peopleware.finan.model.SaldoPatrimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SaldoPatrimonialRepository extends JpaRepository<SaldoPatrimonial, Long> {
    Optional<SaldoPatrimonial> findTopByOrderByIdAsc();
}