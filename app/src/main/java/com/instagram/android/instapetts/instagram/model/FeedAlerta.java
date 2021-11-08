package com.instagram.android.instapetts.instagram.model;

public class FeedAlerta {

    private String id;
    private String fotoPostagem;
    private String descricao;
    private String nomeUsuario;
    private String fotoUsuario;
    private String nomePet;
    private String cidade;
    private String bairro;
    private String cep;
    private String telefone;
    private String email;

    public FeedAlerta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFotoPostagem() {
        return fotoPostagem;
    }

    public void setFotoPostagem(String fotoPostagem) {
        this.fotoPostagem = fotoPostagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
