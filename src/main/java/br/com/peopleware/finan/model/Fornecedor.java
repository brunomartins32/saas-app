package br.com.peopleware.finan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedores")
@PrimaryKeyJoinColumn(name = "cliente_id")
public class Fornecedor extends Cliente {
    private String ramoAtividade;
}