package br.com.peopleware.finan.service;

import br.com.peopleware.finan.dto.FornecedorDTO;
import br.com.peopleware.finan.model.Fornecedor;
import br.com.peopleware.finan.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {
    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Fornecedor save(FornecedorDTO fornecedorDto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDto.getNome());
        fornecedor.setDocumento(fornecedorDto.getDocumento());
        fornecedor.setTipo(fornecedorDto.getTipo());
        return repository.save(fornecedor);
    }

    public List<Fornecedor> findByRamo(String ramo) {
        return repository.findByRamoAtividade(ramo);
    }
}