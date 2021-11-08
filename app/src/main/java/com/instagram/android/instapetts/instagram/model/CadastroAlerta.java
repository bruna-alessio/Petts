package com.instagram.android.instapetts.instagram.model;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.instagram.android.instapetts.instagram.helper.ConfiguracaoFirebase;
import com.instagram.android.instapetts.instagram.helper.UsuarioFirebase;

import java.util.HashMap;
import java.util.Map;

public class CadastroAlerta {

    private String id;
    private String idUsuario;
    private String caminhoFoto;
    private String nomePett;
    private String descricao;
    private String cidade;
    private String bairro;
    private String cep;
    private String email;
    private String telefone;


    public CadastroAlerta() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference postagemRef = firebaseRef.child("postagensalerta");
        String idPostagem = postagemRef.push().getKey();
        setId(idPostagem);


    }

    public boolean salvarAlerta(DataSnapshot usuariosSnapshot) {

        Map objeto = new HashMap();
        Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        /*DatabaseReference postagensalertaRef = firebaseRef.child("postagensalerta")
                .child( getIdUsuario())
                .child( getId() );
        postagensalertaRef.setValue(this);*/

        //Referencia para postagem
        String combinacaoId = "/" + getIdUsuario() + "/" + getId();
        objeto.put("/postagensalerta" + combinacaoId, this );

        //Referencia para postagem no feed alerta
        for ( DataSnapshot usuarios: usuariosSnapshot.getChildren() ) {

            /*
            * feed
            *  id_usuario
            *     id_postagemalerta
            *          postagem
            * */

            //Monta objeto para salvar
            HashMap<String, Object> dadosUsuario = new HashMap<>();
            dadosUsuario.put("fotoPostagem", getCaminhoFoto());
            dadosUsuario.put("descricao", getDescricao());
            dadosUsuario.put("nomePett", getNomePett());
            dadosUsuario.put("cidade", getCidade());
            dadosUsuario.put("bairro", getBairro());
            dadosUsuario.put("cep", getCep());
            dadosUsuario.put("telefone", getTelefone());
            dadosUsuario.put("email", getEmail());
            dadosUsuario.put("idPostagem", getId());
            dadosUsuario.put("nomeUsuario", usuarioLogado.getNome());
            dadosUsuario.put("fotoUsuario", usuarioLogado.getCaminhoFoto());

            String idsAtualizacao = "/" + getIdUsuario() + "/" + getId();
            objeto.put("/feedalerta" + idsAtualizacao, dadosUsuario );

        }

        firebaseRef.updateChildren( objeto );
        return true;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public String getNomePett() {
        return nomePett;
    }


    public void setNomePett(String nomePett) {
        this.nomePett = nomePett;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
