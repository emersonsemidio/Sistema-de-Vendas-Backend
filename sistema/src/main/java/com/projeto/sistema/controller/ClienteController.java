package com.projeto.sistema.controller;

import com.projeto.sistema.dto.ClienteUpdateDTO;
import com.projeto.sistema.model.Cliente;
import com.projeto.sistema.service.ServiceCliente;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.sistema.infra.security.SecurityConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Operações de CRUD para gerenciamento de clientes")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ClienteController {

    @Autowired
    private ServiceCliente serviceCliente;

    // @Valid
    // @PostMapping
    // @Operation(summary = "Criar novo cliente")
    // @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso")
    // public ResponseEntity<MensagemResponseDto> salvar(@RequestBody @Valid ClienteRegisterDto clienteDto) {
    //     try {
    //         Cliente cliente = serviceCliente.convertRegisterDtoToEntity(clienteDto);
    //         Cliente salvo = serviceCliente.salvar(cliente);
    //         MensagemResponseDto mensagem = new MensagemResponseDto("Cliente criado com sucesso", "200", salvo);
    //         return ResponseEntity.ok(mensagem);

    //     } catch (Exception e) {
    //         MensagemResponseDto errorResponse = new MensagemResponseDto(
    //             "Erro ao criar cliente", "400", e.getMessage()
    //         );
    //         return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    //     }
    // }

    @GetMapping
    @Operation(summary = "Listar todos os clientes")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso")
    public ResponseEntity<Iterable<Cliente>> listar() {
        return ResponseEntity.ok(serviceCliente.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = serviceCliente.buscarPorId(id);
        return cliente.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Valid
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, 
                                       @RequestBody @Valid ClienteUpdateDTO clienteDTO) {
    Optional<Cliente> atualizado = serviceCliente.atualizar(id, clienteDTO);
    return atualizado.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
}

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente deletado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        System.out.println("ID recebido para deleção: " + id);
        if (serviceCliente.deletar(id)) {
            System.out.println("Cliente com ID " + id + " deletado com sucesso.");
            return ResponseEntity.noContent().build();
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return ResponseEntity.notFound().build();
        }
    }
}