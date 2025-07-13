package br.com.peopleware.finan.service;

import br.com.peopleware.finan.model.ContaPatrimonial;
import br.com.peopleware.finan.repository.ContaPatrimonialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaPatrimonialService {

    @Autowired
    private ContaPatrimonialRepository repository;

    public List<ContaPatrimonial> listar() {
        return repository.findAll();
    }

    public ContaPatrimonial salvar(ContaPatrimonial conta) {
        return repository.save(conta);
    }

    public Optional<ContaPatrimonial> atualizar(Long id, ContaPatrimonial novaConta) {
        return repository.findById(id).map(conta -> {
            conta.setNome(novaConta.getNome());
            conta.setInstituicao(novaConta.getInstituicao());
            conta.setSaldoAtual(novaConta.getSaldoAtual());
            conta.setTipo(novaConta.getTipo());
            return repository.save(conta);
        });
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    public BigDecimal calcularSaldoTotal() {
        return repository.findAll()
                .stream()
                .map(ContaPatrimonial::getSaldoAtual)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
