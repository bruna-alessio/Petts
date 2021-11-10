package com.instagram.android.instapetts.instagram.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.instagram.android.instapetts.instagram.helper.ConfiguracaoFirebase;
import com.instagram.android.instapetts.instagram.helper.Permissao;
import com.instagram.android.instapetts.instagram.helper.UsuarioFirebase;
import com.instagram.android.instapetts.instagram.model.PostagemAlerta;
import com.instagram.android.instapetts.instagram.model.Usuario;
import com.instagram.android.instapetts.instagram.R;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastrarPetPerdidoActivity extends AppCompatActivity {

    private TextView textAdicionarFoto;
    private static final int SELECAO_GALERIA = 200;
    private CircleImageView imageCadastro;
    private StorageReference storageRef;
    private String identificadorUsuario;
    private Usuario usuarioLogado;
    private String idUsuarioLogado;
    private TextInputEditText editNomePet;
    private TextInputEditText editDescricaoPet;
    private TextInputEditText editCidadePet;
    private TextInputEditText editBairroPet;
    private TextInputEditText editCepPet;
    private TextInputEditText editEmailPet;
    private TextInputEditText editTelPet;
    private DataSnapshot usuariosSnapshot;


    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pet_perdido);

        //Configurações iniciais_/
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1 );

        //Configuracoes iniciais
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();


        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Cadastrar Pett");
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


        //inicializar componentes
        inicializarComponentes();

        //Recuperar dados do usuário
        FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();

        //Adicionar foto do pett perdido
        textAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if ( i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try{

                //Selecao apenas da galeria
                switch ( requestCode ){
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                                break;
                }

                //Caso tenha sido escolhido uma imagem
                if (  imagem != null ){

                    //Configura imagem na tela

                    imageCadastro.setImageBitmap(imagem);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    StorageReference imagemRef = storageRef
                            .child("imagensalerta")
                            .child("postagensalerta")
                            .child( identificadorUsuario + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CadastrarPetPerdidoActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Recuperar local da foto
                            Uri url = taskSnapshot.getDownloadUrl();

                            Toast.makeText(CadastrarPetPerdidoActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void publicarPostagemAlerta(){

        //Recuperando informações de texto cadastro para postagem
        final PostagemAlerta postagem = new PostagemAlerta();
        postagem.setIdUsuario( idUsuarioLogado );
        postagem.setNomePet( editNomePet.getText().toString() );
        postagem.setDescricaoPet( editDescricaoPet.getText().toString() );
        postagem.setCidade( editCidadePet.getText().toString() );
        postagem.setBairro( editBairroPet.getText().toString() );
        postagem.setCep( editCepPet.getText().toString() );
        postagem.setEmail( editEmailPet.getText().toString() );
        postagem.setTelefone( editTelPet.getText().toString() );

        //Recuperar dados da imagem no Firebase
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] dadosImagem = baos.toByteArray();

        //Salvar imagem no firebase storage
        StorageReference storageRef = ConfiguracaoFirebase.getFirebaseStorage();
        StorageReference imagemRef = storageRef
                .child("imagensalerta")
                .child("postagensalerta")
                .child( postagem.getId() + ".jpeg");

        UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastrarPetPerdidoActivity.this,
                        "Erro ao publicar alerta!",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Recuperar local da foto
                Uri url = taskSnapshot.getDownloadUrl();
                postagem.setCaminhoFoto(url.toString());

                if ( postagem.salvarAlerta(usuariosSnapshot) ){
                    Toast.makeText(CadastrarPetPerdidoActivity.this,
                            "Sucesso ao publicar alerta!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ic_salvar_cadastro :
                publicarPostagemAlerta();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp(){

        finish();
        return false;

    }

    public void inicializarComponentes(){

        imageCadastro = findViewById(R.id.imageCadastro);
        textAdicionarFoto = findViewById(R.id.textAdicionarFoto);
        editDescricaoPet = findViewById(R.id.editDescricaoPet);
        editNomePet = findViewById(R.id.editNomePet);
        editCidadePet = findViewById(R.id.editCidadePet);
        editBairroPet = findViewById(R.id.editBairroPet);
        editCepPet = findViewById(R.id.editCepPet);
        editEmailPet = findViewById(R.id.editEmailPet);
        editTelPet = findViewById(R.id.editTelPet);


    }
}
