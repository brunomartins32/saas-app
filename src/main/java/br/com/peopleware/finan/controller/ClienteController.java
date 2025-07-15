package br.com.peopleware.finan.controller;


import br.com.peopleware.finan.dto.ClienteDTO;
import br.com.peopleware.finan.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO clienteDTO){
        clienteService.save(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listAll (){
        List<ClienteDTO> clienteDTOS = clienteService.list();
        return ResponseEntity.status(HttpStatus.OK).body(clienteDTOS);

    }

}
