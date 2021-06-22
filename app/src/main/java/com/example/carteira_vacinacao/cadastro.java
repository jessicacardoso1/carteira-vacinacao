package com.example.carteira_vacinacao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class cadastro extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextNome;
    private EditText editTextDataNasc;
    private EditText editTextCPF;
    private EditText editTextEmail;
    private EditText editTextCelular;
    private EditText editTextEndereco;
    private EditText editTextSenha;
    private Button buttonSalvar;

    private final Usuario usuario = new Usuario(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        editTextNome = (EditText)findViewById(R.id.Nome);
        editTextDataNasc = (EditText)findViewById(R.id.dataNasc);
        editTextCPF = (EditText)findViewById(R.id.cpf);
        editTextEmail = (EditText)findViewById(R.id.email);
        editTextCelular = (EditText)findViewById(R.id.celular);
        editTextEndereco = (EditText)findViewById(R.id.endereco);
        editTextSenha = (EditText)findViewById(R.id.senha);
        buttonSalvar = (Button)findViewById(R.id.salvar);

        buttonSalvar.setOnClickListener(this);
        setTitle(getString(R.string.titulo_cadastrar));

    }

    @Override
    public void onClick(View v) {
        boolean valido = true;
        usuario.setNome(editTextNome.getText().toString());
        usuario.setDataNascimento(editTextDataNasc.getText().toString());
        usuario.setCpf(editTextCPF.getText().toString());
        usuario.setEmail(editTextEmail.getText().toString().trim().toLowerCase());
        usuario.setCelular(editTextCelular.getText().toString());
        usuario.setEndereco(editTextEndereco.getText().toString());
        usuario.setSenha(editTextSenha.getText().toString());

        if(usuario.getNome().equals("")){
            editTextNome.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getDataNascimento().equals("")){
            editTextDataNasc.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getEmail().equals("")){
            editTextEmail.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getCpf().equals("")){
            editTextCPF.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getCelular().equals("")){
            editTextCelular.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getEndereco().equals("")){
            editTextEndereco.setError(getString(R.string.obrigatorio));
            valido = false;
        }
        if(usuario.getSenha().equals("")){
            editTextSenha.setError(getString(R.string.obrigatorio));
            valido = false;
        }

        if(valido){
            usuario.salvar();
            Toast.makeText(cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}