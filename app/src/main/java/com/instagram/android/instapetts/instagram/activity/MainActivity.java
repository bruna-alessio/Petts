package com.instagram.android.instapetts.instagram.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.instagram.android.instapetts.instagram.fragment.AlertaFragment;
import com.instagram.android.instapetts.instagram.fragment.FeedFragment;
import com.instagram.android.instapetts.instagram.fragment.PerfilFragment;
import com.instagram.android.instapetts.instagram.fragment.PesquisaFragment;
import com.instagram.android.instapetts.instagram.fragment.PostagemFragment;
import com.instagram.android.instapetts.instagram.helper.ConfiguracaoFirebase;
import com.instagram.android.instapetts.instagram.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Petts");
        setSupportActionBar( toolbar );

        //configuracoes de objetos
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


        //Configurar bottom navigation view
        configuraBottomNavigationView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();

    }

    /**
     * Método responsável por criar a BottomNavigation
     */
    private void configuraBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);

        //faz configurações iniciais do Bottom Navigation
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);


        //Habilitar navegação
        habilitarNavegacao( bottomNavigationViewEx );

        //configura item selecionado inicialmente
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

    }

    /**
     * Método responsável por tratar eventos de click na BottomNavigation
     * @param viewEx
     */
    private void habilitarNavegacao(BottomNavigationViewEx viewEx){

        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.ic_home :
                        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
                        return true;
                    case R.id.ic_pesquisa :
                        fragmentTransaction.replace(R.id.viewPager, new PesquisaFragment()).commit();
                        return true;
                    case R.id.ic_postagem :
                        fragmentTransaction.replace(R.id.viewPager, new PostagemFragment()).commit();
                        return true;
                    case R.id.ic_perfil :
                        fragmentTransaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                        return true;

                    case R.id.ic_alerta :
                        fragmentTransaction.replace(R.id.viewPager, new AlertaFragment()).commit();
                        return true;



                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void onCreateOptionsMenu( @NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
/**
   @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_camera);
        item.setVisible(true);
        return false;
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_sair :
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }





    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
