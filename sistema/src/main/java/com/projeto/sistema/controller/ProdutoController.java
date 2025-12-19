package com.projeto.sistema.controller;

import com.projeto.sistema.dto.MensagemResponseDto;
import com.projeto.sistema.dto.ProdutoRegisterDto;
import com.projeto.sistema.dto.ProdutoUpdateDto;
import com.projeto.sistema.infra.security.SecurityConfiguration;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.service.ProdutoService;
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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações de CRUD para gerenciamento de produtos")
@SecurityRequirement(name = SecurityConfiguration.SECURITY)
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ServiceMercado serviceUsuario;

    @GetMapping
    @Operation(
        summary = "Listar todos os produtos",
        description = "Retorna uma lista com todos os produtos cadastrados no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produtos listados com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor"
        )
    })

    public Iterable<Produto> listarTodos() {
        return produtoService.listarTodos();
    }
    

    @GetMapping("/estoqueBaixo")
    @Operation(
            summary = "Listar todos os produtos com estoque baixo",
            description = "Retorna uma lista com todos os produtos com estoque baixo no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Produtos listados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Produto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor"
            )
    })

    public Iterable<Produto> listarProdutosComEstoqueBaixo() {
        return produtoService.listarProdutosComEstoqueBaixo();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar produto por ID",
        description = "Retorna um produto específico baseado no ID fornecido"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado com sucesso",
            content = @Content(mediaType = "application/json", 
                             schema = @Schema(implementation = Produto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo produto")
    @ApiResponse(responseCode = "200", description = "Produto criado com sucesso")
    public ResponseEntity<MensagemResponseDto> salvar(@RequestBody @Valid ProdutoRegisterDto produto) {
        try {
            serviceUsuario.buscarPorId(produto.getMercadoId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Produto novoProduto = produtoService.convertRegisterDtoToEntity(produto);
            Produto salvo = produtoService.salvar(novoProduto);
            MensagemResponseDto mensagem = new MensagemResponseDto("Produto criado com sucesso", "200", salvo);
            return ResponseEntity.ok(mensagem);

        } catch (Exception e) {
            MensagemResponseDto errorResponse = new MensagemResponseDto(
                "Erro ao criar produto", "400", e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoUpdateDto produtoDto) {
        return produtoService.atualizar(id, produtoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar produto",
        description = "Remove um produto do sistema baseado no ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Produto deletado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado"
        )
    })
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        boolean deletado = produtoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}