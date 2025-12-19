package com.projeto.sistema.controller;

import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.dto.MercadoRegisterDto;
import com.projeto.sistema.dto.UsuarioUpdateDto;
import com.projeto.sistema.infra.security.SecurityConfiguration;
import com.projeto.sistema.model.Mercado;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.service.ServiceMercado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mercados")
@Tag(name = "Mercados", description = "Operações de CRUD para gerenciamento de mercados")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class MercadoController {

    @Autowired
    private ServiceMercado mercadoService;

    @GetMapping
    @Operation(summary = "Listar todos os mercados")
    @ApiResponse(responseCode = "200", description = "Mercados listados com sucesso")
    public ResponseEntity<Iterable<Mercado>> listarTodos() {
        System.out.println("Listando todos os mercados...");
        return ResponseEntity.ok(mercadoService.listarTodos());
    }

    @GetMapping("/{id}/produtos")
    @Operation(summary = "Listar todos os produtos de um mercado")
    @ApiResponse(responseCode = "200", description = "Produtos listados com sucesso")
    public ResponseEntity<Iterable<Produto>> listarProdutosPorMercado(@PathVariable Long id) {
        return ResponseEntity.ok(mercadoService.listarProdutosPorMercado(id));
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar mercado por nome")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado encontrado"),
        @ApiResponse(responseCode = "404", description = "Mercado não encontrado")
    })
    public ResponseEntity<Mercado> buscarPorNome(@PathVariable String nome) {
        return mercadoService.buscarPorNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mercado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado encontrado"),
        @ApiResponse(responseCode = "404", description = "Mercado não encontrado")
    })
    public ResponseEntity<Mercado> buscarPorId(@PathVariable Long id) {
        return mercadoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar novo Mercado")
    @ApiResponse(responseCode = "200", description = "Mercado criado com sucesso")
    public ResponseEntity<MensagemResponseDto> salvar(@RequestBody MercadoRegisterDto mercadoDto) {
        try {
            Mercado mercado = mercadoService.convertRegisterDtoToEntity(mercadoDto);
            Mercado salvo = mercadoService.criar(mercado);
            MensagemResponseDto mensagem = new MensagemResponseDto("Mercado criado com sucesso", "200", salvo);
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            MensagemResponseDto errorResponse = new MensagemResponseDto(
                "Erro ao criar mercado", "400", e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar mercado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado atualizado"),
        @ApiResponse(responseCode = "404", description = "Mercado não encontrado")
    })
    public ResponseEntity<Mercado> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioUpdateDto usuarioDto) {
        return mercadoService.atualizar(id, usuarioDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar mercado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mercado deletado"),
        @ApiResponse(responseCode = "404", description = "Mercado não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (mercadoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}