package com.projeto.sistema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public class ProdutoRegisterDto {

  @NotBlank(message = "O campo nome é obrigatorio")
  @Size(min = 3, max = 50, message = "O nome do produto deve ter entre 3 e 50 caracteres")
  private String nome;

  @NotBlank(message = "O campo descricao é obrigatorio")
  @Size(min = 10, max = 200, message = "A descrição deve ter entre 10 e 200 caracteres")
  private String descricao;

  @NotNull(message = "O campo preço é obrigatorio")
  @Positive(message = "O campo preço deve ser um número positivo")
  private Double preco;

  @NotNull(message = "O campo quantidade é obrigatorio")
  @Positive(message = "O campo quantidade deve ser um número positivo")
  private Integer quantidade;

  private String imagemUrl;

  @NotNull(message = "O campo nomeMercado é obrigatorio")
  private String nomeMercado;

  // @NotNull(message = "O campo usuarioId é obrigatorio")
  // private Long mercadoId;

  

  // Getters and Setters
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public String getImagemUrl() {
    return imagemUrl;
  }

  public void setImagemUrl(String imagemUrl) {
    this.imagemUrl = imagemUrl;
  }

  // public Long getMercadoId() {
  //   return mercadoId;
  // }

  // public void setMercadoId(Long usuarioId) {
  //   this.mercadoId = usuarioId;
  // }

  public String getNomeMercado() {
    return nomeMercado;
  }

  public void setNomeMercado(String nomeMercado) {
    this.nomeMercado = nomeMercado;
  }
  

  @Override
    public String toString() {
        return "ProdutoRegisterDto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }

}
