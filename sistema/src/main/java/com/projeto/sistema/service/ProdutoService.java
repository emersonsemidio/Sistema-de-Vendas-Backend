package com.projeto.sistema.service;

import com.projeto.sistema.dto.ProdutoRegisterDto;
import com.projeto.sistema.dto.ProdutoUpdateDto;
import com.projeto.sistema.model.Mercado;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repo.RepoMercado;
import com.projeto.sistema.repo.RepoProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private RepoProduto produtoRepository;

    @Autowired
    private RepoMercado repoMercado;

    // Listar todos os produtos
    public Iterable<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarProdutosComEstoqueBaixo() {
        return produtoRepository.findByQuantidadeLessThanEqual(10);
    }

    // Buscar produto por ID
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    // Criar novo produto
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Atualizar produto existente
    public Optional<Produto> atualizar(Long id, ProdutoUpdateDto dto) {
        return produtoRepository.findById(id).map(produtoExistente -> {

            if (dto.getNome() != null) {
                produtoExistente.setNome(dto.getNome());
            }
            if (dto.getDescricao() != null) {
                produtoExistente.setDescricao(dto.getDescricao());
            }
            if (dto.getPreco() != null) {
                produtoExistente.setPreco(dto.getPreco());
            }
            if (dto.getQuantidade() != null) {
                produtoExistente.setQuantidade(dto.getQuantidade());
            }
            if (dto.getImagemUrl() != null) {
                produtoExistente.setImagemUrl(dto.getImagemUrl());
            }

            return produtoRepository.save(produtoExistente);
        });
    }

    public Produto convertUpdateDtoToEntity(ProdutoUpdateDto dto) {
        Produto produto = new Produto();
        // Apenas seta os campos que vieram no DTO
        produto.setNome(dto.getNome()); // Pode ser null - não problema
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setImagemUrl(dto.getImagemUrl());
        return produto;
    }


    public Produto convertRegisterDtoToEntity(ProdutoRegisterDto dto) {
        Produto produto = new Produto();

        Mercado mercado = repoMercado.findById(dto.getMercadoId())
                .orElseThrow(() -> new RuntimeException("Mercado não encontrado"));
        // Apenas seta os campos que vieram no DTO
        produto.setNome(dto.getNome()); // Pode ser null - não problema
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        produto.setImagemUrl(dto.getImagemUrl());
        produto.setMercado(mercado);
        return produto;
    }

    // Deletar produto por ID
    public boolean deletar(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
