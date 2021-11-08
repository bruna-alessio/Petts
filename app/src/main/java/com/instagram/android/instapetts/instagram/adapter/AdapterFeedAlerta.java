package com.instagram.android.instapetts.instagram.adapter;

import android.content.Intent;
import android.net.Uri;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.instagram.android.instapetts.instagram.helper.UsuarioFirebase;
import com.instagram.android.instapetts.instagram.model.FeedAlerta;
import com.instagram.android.instapetts.instagram.model.Usuario;
import com.instagram.android.instapetts.instagram.R;
import com.instagram.android.instapetts.instagram.activity.ComentariosActivity;
import com.like.LikeButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFeedAlerta extends RecyclerView.Adapter<AdapterFeedAlerta.MyViewHolder> {

    private Context context;
    private List<FeedAlerta> listaFeedAlerta;



    public AdapterFeedAlerta( List<FeedAlerta> listaFeedAlerta, Context context) {
        this.listaFeedAlerta = listaFeedAlerta;
        this.context = context;
    }


    @Override
    public AdapterFeedAlerta.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed_alerta, parent, false);
        return new AdapterFeedAlerta.MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(AdapterFeedAlerta.MyViewHolder holder, int position) {

        final FeedAlerta feedAlerta = listaFeedAlerta.get(position);
        Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Carrega dados do feed
        Uri uriFotoUsuario = Uri.parse( feedAlerta.getFotoUsuario() );
        Uri uriFotoPostagem = Uri.parse( feedAlerta.getFotoPostagem() );

        Glide.with( context ).load( uriFotoUsuario ).into(holder.fotoPerfil);
        Glide.with( context ).load( uriFotoPostagem ).into(holder.fotoPostagem);

        holder.descricao.setText( feedAlerta.getDescricao() );
        holder.nome.setText( feedAlerta.getNomeUsuario() );

        //Adiciona evento de clique nos comentários
        holder.visualizarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComentariosActivity.class);
                i.putExtra("idPostagem", feedAlerta.getId() );
                context.startActivity( i );
            }
        });

        /*
        postagens-curtidas
            + id_postagem
                + qtdCurtidas
                + id_usuario
                    nome_usuario
                    caminho_foto
        * */


    }

    @Override
    public int getItemCount() {

        return listaFeedAlerta.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView fotoPerfil;
        TextView nome, descricao, qtdCurtidas, nomePet, cidade, bairro, cep, email, telefone;
        ImageView fotoPostagem, visualizarComentario;
        LikeButton likeButton;

        public MyViewHolder(View itemView) {
            super(itemView);

            fotoPerfil   = itemView.findViewById(R.id.imagePerfilPostagem);
            fotoPostagem = itemView.findViewById(R.id.imagePostagemSelecionada);
            nome         = itemView.findViewById(R.id.textPerfilPostagem);
            qtdCurtidas  = itemView.findViewById(R.id.textQtdCurtidasPostagem);
            nomePet    = itemView.findViewById(R.id.editNomePet);
            descricao    = itemView.findViewById(R.id.editDescricaoPet);
            cidade    = itemView.findViewById(R.id.editCidadePet);
            bairro    = itemView.findViewById(R.id.editBairroPet);
            cep    = itemView.findViewById(R.id.editCepPet);
            email    = itemView.findViewById(R.id.editCadastroEmail);
            telefone    = itemView.findViewById(R.id.editTelPet);
            visualizarComentario    = itemView.findViewById(R.id.imageComentarioFeed);
            likeButton = itemView.findViewById(R.id.likeButtonFeed);
        }
    }
}
