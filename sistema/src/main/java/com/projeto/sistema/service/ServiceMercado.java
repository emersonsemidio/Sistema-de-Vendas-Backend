package com.projeto.sistema.service;

import com.projeto.sistema.dto.MercadoRegisterDto;
import com.projeto.sistema.dto.UsuarioUpdateDto;
import com.projeto.sistema.model.Mercado;
import com.projeto.sistema.model.Produto;
import com.projeto.sistema.repo.RepoMercado;
import com.projeto.sistema.repo.RepoProduto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceMercado {

    @Autowired
    private RepoMercado repoMercado;

    @Autowired
    private RepoProduto repoProduto;

    public Iterable<Mercado> listarTodos() {
        return repoMercado.findAll();
    }

    public Iterable<Produto> listarProdutosPorMercado(Long mercadoId) {
        return repoProduto.findByMercadoId(mercadoId).orElseThrow(() -> new RuntimeException("Ocorreu um erro ao buscar produtos para o mercado com ID: " + mercadoId));
    }

    public Optional<Mercado> buscarPorId(Long id) {
        return repoMercado.findById(id);
    }

    public Mercado criar(Mercado usuario) {
        return repoMercado.save(usuario);
    }

    public Optional<Mercado> atualizar(Long id, UsuarioUpdateDto dto) {
        return repoMercado.findById(id).map(usuarioExistente -> {

            if (dto.getNome() != null) {
                usuarioExistente.setNome(dto.getNome());
            }
            if (dto.getEmail() != null) {
                usuarioExistente.setEmail(dto.getEmail());
            }
            if (dto.getSenha() != null) {
                usuarioExistente.setCnpj(dto.getSenha());
            }

            return repoMercado.save(usuarioExistente);
        });
    }

    public Mercado convertUpdateDtoToEntity(UsuarioUpdateDto dto) {
        Mercado usuario = new Mercado();
        // Apenas seta os campos que vieram no DTO
        usuario.setNome(dto.getNome()); // Pode ser null - não problema
        usuario.setEmail(dto.getEmail());
        usuario.setCnpj(dto.getSenha());
        return usuario;
    }

    public Mercado convertRegisterDtoToEntity(MercadoRegisterDto dto) {
        Mercado mercado = new Mercado();
        // Apenas seta os campos que vieram no DTO
        mercado.setNome(dto.getNome()); // Pode ser null - não problema
        mercado.setEmail(dto.getEmail());
        mercado.setCnpj(dto.getCnpj());
        mercado.setTelefone(dto.getTelefone());
        mercado.setEndereco(dto.getEndereco());
        mercado.setImagemUrl(dto.getImagemUrl());
        return mercado;
    }

    public boolean deletar(Long id) {
        return repoMercado.findById(id).map(usuario -> {
            repoMercado.deleteById(id);
            return true;
        }).orElse(false);
    }
}
