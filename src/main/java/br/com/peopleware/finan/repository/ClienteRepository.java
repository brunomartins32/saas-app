package br.com.peopleware.finan.repository;

import br.com.peopleware.finan.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}