package br.com.peopleware.finan.service;

import br.com.peopleware.finan.dto.ClienteDTO;
import br.com.peopleware.finan.model.Cliente;
import br.com.peopleware.finan.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente save(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteDto.getId());
        cliente.setNome(clienteDto.getNome());
        cliente.setDocumento(clienteDto.getDocumento());
        cliente.setTipo(clienteDto.getTipo());
        cliente.setAtivo(clienteDto.isAtivo());
        return repository.save(cliente);
    }



    @Transactional
    public List<ClienteDTO> list(){
        List<Cliente> clientes = repository.findAll();
        return clientes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ClienteDTO convertToDTO(Cliente cliente){
    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setId(cliente.getId());
    clienteDTO.setAtivo(cliente.isAtivo());
    clienteDTO.setDocumento(cliente.getDocumento());
    clienteDTO.setTipo(cliente.getTipo());
    clienteDTO.setNome(cliente.getNome());
    return clienteDTO;
    }
}