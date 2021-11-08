package com.instagram.android.instapetts.instagram.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.instagram.android.instapetts.instagram.R;
import com.instagram.android.instapetts.instagram.adapter.AdapterFeedAlerta;
import com.instagram.android.instapetts.instagram.helper.ConfiguracaoFirebase;
import com.instagram.android.instapetts.instagram.helper.UsuarioFirebase;
import com.instagram.android.instapetts.instagram.model.FeedAlerta;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertaFragment extends Fragment {

    private RecyclerView recyclerFeedAlerta;
    private AdapterFeedAlerta adapterFeedAlerta;
    private List<FeedAlerta> listaFeedAlerta = new ArrayList<>();
    private ValueEventListener valueEventListenerFeedAlerta;
    private DatabaseReference feedalertaRef;
    private String idUsuarioLogado;

    public AlertaFragment() {
        // Required empty public constructor
    }



    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alerta, container, false);

               idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        feedalertaRef = ConfiguracaoFirebase.getFirebase()
                .child("feedalerta")
                .child( idUsuarioLogado );

        recyclerFeedAlerta = view.findViewById(R.id.recyclerFeedAlerta);

        adapterFeedAlerta = new AdapterFeedAlerta(listaFeedAlerta, getActivity() );
        recyclerFeedAlerta.setHasFixedSize(true);
        recyclerFeedAlerta.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFeedAlerta.setAdapter( adapterFeedAlerta );

        return view;
    }

    private void listarFeedAlerta(){

        valueEventListenerFeedAlerta = feedalertaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    listaFeedAlerta.add( ds.getValue(FeedAlerta.class));
                }
                adapterFeedAlerta.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        listarFeedAlerta();
    }

    @Override
    public void onStop() {
        super.onStop();
        feedalertaRef.removeEventListener( valueEventListenerFeedAlerta );
    }

}