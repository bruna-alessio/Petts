package com.instagram.android.instapetts.instagram.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.instagram.android.instapetts.instagram.helper.ConfiguracaoFirebase;
import com.instagram.android.instapetts.instagram.helper.UsuarioFirebase;

import java.util.HashMap;
import java.util.Map;

public class PostagemAlerta {

    /*
    * Modelo postagem de alerta
    * postagens alerta
    *  id usuario
    *   id postagem firebase
    *     caminho foto
    *       nome pet
    *         descricao pet
    *           cidade
    *             bairro
    *               cep
    *                 email
    *                   telefone
    * */

    private String id;
    private String idUsuario;
    private String caminhoFoto;
    private String nomePet;
    private String descricaoPet;
    private String cidade;
    private String bairro;
    private String cep;
    private String email;
    private String telefone;


    public PostagemAlerta() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference postagemalertaRef = firebaseRef.child("postagensalerta");
        String idPostagemAlerta = postagemalertaRef.push().getKey();
        setId(idPostagemAlerta);

    }

    public boolean salvarAlerta( DataSnapshot usuariosSnapshot ){

        Map objeto = new HashMap();
        Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

       /*
        DatabaseReference postagensalertaRef = firebaseRef.child("postagensalerta")
                .child( getIdUsuario() )
                .child( getId() );

        postagensalertaRef.setValue(this);
        */

       String combinacaoId = "/" + getIdUsuario() + "/" +getId();
       objeto.put("/postagensalerta" + combinacaoId, this);
       //objeto.put("/feedalerta" + combinacaoId, this);

        for( DataSnapshot usuarios: usuariosSnapshot.getChildren() ){

            /*
            + feed
              + id_seguidor<jose renato>
                + id_postagem <01>
                    postagem< por jamilton>
           */
            String idSeguidor = usuarios.getKey();

            //Monta objeto para salvar
            HashMap<String, Object> dadosUsuario = new HashMap<>();
            dadosUsuario.put("fotoPostagem", getCaminhoFoto() );
            dadosUsuario.put("descricao", getDescricaoPet() );
            dadosUsuario.put("id", getId() );
            dadosUsuario.put("nomeUsuario", usuarioLogado.getNome() );
            dadosUsuario.put("fotoUsuario", usuarioLogado.getCaminhoFoto() );

            String idsAtualizacao = "/" + idSeguidor + "/" + getId();
            objeto.put("/feed" + idsAtualizacao, dadosUsuario  );

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

    public String getNomePet() {
        return nomePet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public String getDescricaoPet() {
        return descricaoPet;
    }

    public void setDescricaoPet(String descricaoPet) {
        this.descricaoPet = descricaoPet;
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
