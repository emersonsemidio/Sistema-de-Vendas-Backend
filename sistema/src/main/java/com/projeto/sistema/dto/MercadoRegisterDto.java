package com.projeto.sistema.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class MercadoRegisterDto {

    @NotNull
    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
    private String nome;

    @NotNull
    @NotBlank(message = "O campo email é obrigatório")
    @Email(message = "O campo email deve ser um email válido")
    private String email;

    @NotNull
    @NotBlank(message = "O campo cnpj é obrigatório")
    @Size(min = 14, max = 18, message = "O campo deve ter entre 14 e 18 caracteres")
    private String cnpj;

    @NotNull
    @NotBlank(message = "O campo telefone é obrigatório")
    @Size(min = 3, max = 50, message = "O campo deve ter entre 3 e 50 caracteres")
    private String telefone;

    @NotNull
    @NotBlank(message = "O campo endereco é obrigatório")
    @Size(min = 3, max = 100, message = "O campo deve ter entre 3 e 100 caracteres")
    private String endereco;

    @Size(min = 5, max = 100, message = "A URL da imagem deve ter entre 5 e 100 caracteres")
    private String imagemUrl;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

}
