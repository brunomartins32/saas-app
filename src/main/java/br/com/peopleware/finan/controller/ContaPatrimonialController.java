package br.com.peopleware.finan.controller;

import br.com.peopleware.finan.model.ContaPatrimonial;
import br.com.peopleware.finan.service.ContaPatrimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/patrimonios")

@CrossOrigin(origins = "http://localhost:4200")
public class ContaPatrimonialController {

    @Autowired
    private ContaPatrimonialService service;

    @GetMapping
    public List<ContaPatrimonial> listar() {
        return service.listar();
    }

    @PostMapping
    public ContaPatrimonial salvar(@RequestBody ContaPatrimonial conta) {
        return service.salvar(conta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaPatrimonial> atualizar(@PathVariable Long id, @RequestBody ContaPatrimonial conta) {
        return service.atualizar(id, conta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.deletar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/saldo-total")
    public BigDecimal saldoTotal() {
        return service.calcularSaldoTotal();
    }

}
