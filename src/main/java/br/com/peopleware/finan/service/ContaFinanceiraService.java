package br.com.peopleware.finan.service;

import br.com.peopleware.finan.model.ContaFinanceira;
import br.com.peopleware.finan.model.SaldoPatrimonial;
import br.com.peopleware.finan.model.enums.TipoConta;
import br.com.peopleware.finan.repository.ContaFinanceiraRepository;
import br.com.peopleware.finan.repository.SaldoPatrimonialRepository;
import br.com.peopleware.finan.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaFinanceiraService {

    @Autowired
    private ContaFinanceiraRepository contaRepository;

    @Autowired
    private SaldoPatrimonialRepository saldoRepository;

    public List<ContaFinanceira> listar() {
        return contaRepository.findAll();
    }

    public ContaFinanceira salvar(ContaFinanceira conta) {
        if (conta.getId() == null || conta.getId() == 0) {
            // Força um novo ID
            conta.setId(null);
        }
        return contaRepository.save(conta);
    }
    public List<ContaFinanceira> listarContasAtivas() {
        return contaRepository.findByEfetivadaFalseAndEncerradaFalse();
    }

    public List<ContaFinanceira> listarContasEfetivadas() {
        return contaRepository.findByEfetivadaTrue();
    }

    public Optional<ContaFinanceira> atualizar(Long id, ContaFinanceira conta) {
        return contaRepository.findById(id).map(c -> {
            c.setDescricao(conta.getDescricao());
            c.setTipo(conta.getTipo());
            c.setValor(conta.getValor());
            c.setDataVencimento(conta.getDataVencimento());
            c.setQuitada(conta.isQuitada());
            return contaRepository.save(c);
        });
    }
    public ContaFinanceira quitarConta(Long id) {
        return contaRepository.findById(id)
                .map(conta -> {
                    if(conta.isQuitada()) {
                        throw new BusinessException("Conta já está quitada");
                    }
                    conta.setQuitada(true);
                    return contaRepository.save(conta);
                })
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));
    }
    public boolean deletar(Long id) {
        if (contaRepository.existsById(id)) {
            contaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public ContaFinanceira efetivarConta(Long id) {
        ContaFinanceira conta = contaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Conta não encontrada"));

        if(!conta.isQuitada()) {
            throw new BusinessException("Conta precisa estar quitada para efetivar");
        }

        if(conta.isEfetivada()) {
            throw new BusinessException("Conta já está efetivada");
        }

        // Marca como efetivada
        conta.setEfetivada(true);

        // Atualiza o saldo patrimonial
        atualizarSaldoPatrimonial(conta);

        // Marca como "encerrada" (opcional, se quiser manter controle)
        conta.setEncerrada(true);

        return contaRepository.save(conta);
    }
    private void atualizarSaldoPatrimonial(ContaFinanceira conta) {
        SaldoPatrimonial saldo = saldoRepository.findTopByOrderByIdAsc()
                .orElseGet(() -> saldoRepository.save(new SaldoPatrimonial(BigDecimal.ZERO)));

        BigDecimal ajuste;

        if(conta.getTipo() == TipoConta.A_RECEBER) {
            ajuste = conta.getValor();
        } else {
            ajuste = conta.getValor().negate();
        }

        saldo.setSaldo(saldo.getSaldo().add(ajuste));
        saldoRepository.save(saldo);
    }
}