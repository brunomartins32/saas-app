package br.com.peopleware.finan.repository;

import br.com.peopleware.finan.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findByRamoAtividade(String ramo);
}