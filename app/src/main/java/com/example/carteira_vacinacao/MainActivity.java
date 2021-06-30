package com.example.carteira_vacinacao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carteira_vacinacao.dao.CadastroDAO;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private EditText EditTexCpf;
    private EditText EditTextSenha;
    private SharedPreferences myPrefs=null;

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of permissions granted.
        }

        // Obtain the FirebaseAnalytics instance.
        FirebaseApp.initializeApp(MainActivity.this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(MainActivity.this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, "onCreate");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);

        Bundle bundle1 = new Bundle();
        bundle1.putString(FirebaseAnalytics.Param.ITEM_ID, "teste_id");
        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "teste_name");
        bundle1.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "teste_image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);

        EditTexCpf = (EditText) findViewById(R.id.cpfLogin);
        EditTextSenha = (EditText) findViewById(R.id.senhaLogin);
        button = (Button) findViewById(R.id.login);
        button2 = (Button) findViewById(R.id.cadastrar);

        myPrefs = getSharedPreferences("myPrefs",MODE_PRIVATE);

        String cpf = myPrefs.getString("cpf","");
        if(cpf!=null){
            EditTexCpf.setText(cpf);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    autenticaUsuario(EditTexCpf.getText().toString(), EditTextSenha.getText().toString());
                    SharedPreferences.Editor ePrefs = myPrefs.edit();
                    ePrefs.putString("cpf",MainActivity.this.EditTexCpf.getText().toString());
                    ePrefs.commit();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(FirebaseAnalytics.Param.METHOD, "login_button");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle2);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarActivityCadastrar();
            }
        });

    }

    private void autenticaUsuario(String cpf, String senha){
        Usuario usuario = new Usuario(this);
        usuario.setCpf(cpf);
        usuario.setSenha(senha);

        CadastroDAO cadastroDAO = new CadastroDAO(this);
        boolean resultado = cadastroDAO.autenticaUsuario(usuario);
        if (resultado == true){
            criarActivityCarteira(usuario);
            finish();
        }else{
            EditTexCpf.setText("");
            EditTextSenha.setText("");
            EditTexCpf.requestFocus();
            Toast.makeText(this,"Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();

        }

    }
    public void criarActivityCarteira(Usuario usuario) {
        Intent intent = new Intent(this, carteira.class);
        intent.putExtra("consulta",usuario.getCpf());
        startActivity(intent);
    }


    public void criarActivityCadastrar(){
        Intent intent = new Intent(this, cadastro.class);
        startActivity(intent);
    }

    private boolean checkAndRequestPermissions(){
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        int diskPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        List<String> listPermissionsNeeded = new ArrayList<>();
        //if (locationPermission != PackageManager.PERMISSION_GRANTED){
        //  listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        //}
        //if (permissionSendMessage != PackageManager.PERMISSION_GRANTED){
        //  listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        //}
        if (diskPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
/*
    @Override
    public void onClick(View v) {

    }*/
}

