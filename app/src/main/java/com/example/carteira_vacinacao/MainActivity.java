package com.example.carteira_vacinacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carteira_vacinacao.dao.CadastroDAO;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private EditText EditTexCpf;
    private EditText EditTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String name = et.getText().toString();

        EditTexCpf = (EditText) findViewById(R.id.cpf);
        EditTextSenha = (EditText) findViewById(R.id.senha);
        button = (Button) findViewById(R.id.login);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    autenticaUsuario(EditTexCpf.getText().toString(), EditTextSenha.getText().toString());
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        button2 = (Button) findViewById(R.id.cadastrar);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarActivityCadastrar();
            }
        });

    }

    public void criarActivityLogin() {
        Intent intent = new Intent(this, carteira.class);
        startActivity(intent);
    }


    public void criarActivityCadastrar(){
        Intent intent = new Intent(this, cadastro.class);
        startActivity(intent);
    }

    private void autenticaUsuario(String cpf, String senha){
        Usuario usuario = new Usuario(this);
        usuario.setCpf(cpf);
        usuario.setSenha(senha);

        CadastroDAO cadastroDAO = new CadastroDAO(this);
        boolean resultado = cadastroDAO.autenticaUsuario(usuario);
        if (resultado == true){
            criarActivityLogin();
            finish();
        }else{
            EditTexCpf.setText("");
            EditTextSenha.setText("");
            EditTexCpf.requestFocus();
            Toast.makeText(this,"Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();

        }

    }
/*
    @Override
    public void onClick(View v) {

    }*/
}

