package br.com.peopleware.finan.controller;

import br.com.peopleware.finan.exception.BusinessException;
import br.com.peopleware.finan.model.ContaFinanceira;
import br.com.peopleware.finan.service.ContaFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas-financeiras")
@CrossOrigin(origins = "http://localhost:4200")
public class ContaFinanceiraController {

    @Autowired
    private ContaFinanceiraService service;



    @GetMapping
    public List<ContaFinanceira> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<ContaFinanceira> salvar(@RequestBody ContaFinanceira conta) {
        try {
            ContaFinanceira contaSalva = service.salvar(conta);
            return ResponseEntity.ok(contaSalva);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaFinanceira> atualizar(@PathVariable Long id, @RequestBody ContaFinanceira conta) {
        return service.atualizar(id, conta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/efetivar/{id}")
    public ResponseEntity<?> efetivarConta(@PathVariable Long id) {
        try {
            ContaFinanceira contaEfetivada = service.efetivarConta(id);
            return ResponseEntity.ok(contaEfetivada);
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/quitar")
    public ResponseEntity<?> quitarConta(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.quitarConta(id));
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<ContaFinanceira>> listarAtivas() {
        return ResponseEntity.ok(service.listarContasAtivas());
    }

    @GetMapping("/efetivadas")
    public ResponseEntity<List<ContaFinanceira>> listarEfetivadas() {
        return ResponseEntity.ok(service.listarContasEfetivadas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
