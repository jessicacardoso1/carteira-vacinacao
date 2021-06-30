package com.example.carteira_vacinacao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

public class carteira extends AppCompatActivity {
    private Button button;
    private TextView TextViewNome;
    private Usuario usuario;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minha_carteira);


        button = (Button) findViewById(R.id.nomeVacina);
        TextViewNome = (TextView) findViewById(R.id.NomeUsuario);
        usuario = new Usuario(this);
        setTitle(getString(R.string.titulo_carteira));

        String cpf =  getIntent().getExtras().getString("consulta");
        usuario.carregaUsuarioPeloCPF(cpf);
        TextViewNome.setText(usuario.getNome());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarActivityDetalhesVacina();
                Bundle bundle1 = new Bundle();
                bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "vacina_covid");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle1);
            }
        });
    }

    public void criarActivityDetalhesVacina(){
        Intent intent = new Intent(this, detalhes_vacina.class);
        startActivity(intent);
    }
}
