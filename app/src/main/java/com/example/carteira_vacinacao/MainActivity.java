package com.example.carteira_vacinacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences myPrefs=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String name = et.getText().toString();

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
/*
    @Override
    public void onClick(View v) {

    }*/
}

