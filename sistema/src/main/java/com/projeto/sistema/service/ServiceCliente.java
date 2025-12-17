package com.projeto.sistema.service;

import com.projeto.sistema.dto.ClienteUpdateDTO;
import com.projeto.sistema.dto.ClienteRegisterDto;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.repo.RepoCliente;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ServiceCliente {

    @Autowired
    private RepoCliente repoCliente;

    // Criar ou atualizar
    public Cliente salvar(Cliente cliente) {
        return repoCliente.save(cliente);
    }

    // Buscar todos
    public Iterable<Cliente> listarTodos() {
        return repoCliente.findAll();
    }

    // Buscar por ID
    public Optional<Cliente> buscarPorId(Long id) {
        return repoCliente.findById(id);
    }

    // Atualizar (só salva se o ID já existir)

    public Optional<Cliente> atualizar(Long id, ClienteUpdateDTO dto) {
        return repoCliente.findById(id).map(clienteExistente -> {
            
            if (dto.getNome() != null) {
                clienteExistente.setNome(dto.getNome());
            }
            if (dto.getEmail() != null) {
                clienteExistente.setEmail(dto.getEmail());
            }
            if (dto.getTelefone() != null) {
                clienteExistente.setTelefone(dto.getTelefone());
            }
            if (dto.getEndereco() != null) {
                clienteExistente.setEndereco(dto.getEndereco());
            }
            
            return repoCliente.save(clienteExistente);
        });
    }

    public Cliente convertUpdateDtoToEntity(ClienteUpdateDTO dto) {
        Cliente cliente = new Cliente();
        // Apenas seta os campos que vieram no DTO
        cliente.setNome(dto.getNome()); // Pode ser null - não problema
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(dto.getEndereco());
        return cliente;
    }

    public Cliente convertRegisterDtoToEntity(ClienteRegisterDto dto) {
        Cliente cliente = new Cliente();
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getSenha());
        // Apenas seta os campos que vieram no DTO
        cliente.setNome(dto.getNome()); // Pode ser null - não problema
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEndereco(dto.getEndereco());
        cliente.setCpf(dto.getCpf());
        cliente.setSenha(encryptedPassword);
        return cliente;
    }
    
    // Remover
    public boolean deletar(Long id) {
        boolean existe = repoCliente.existsById(id);
        System.out.println("Cliente existe: " + existe);
        this.logTodosClients();
        if (existe) {
            repoCliente.deleteById(id);
            return true;
        }
        return false;
    }

    public void logTodosClients() {
        Iterable<Cliente> clientes = listarTodos();
        System.out.println("Lista de todos os clientes:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }
}
