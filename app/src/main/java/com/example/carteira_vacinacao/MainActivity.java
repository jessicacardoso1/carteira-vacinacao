package com.example.carteira_vacinacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EditText et = (EditText) findViewById(R.id.txtSub);
        //String name = et.getText().toString();

        button = (Button) findViewById(R.id.login);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarActivityLogin();
            }
        });

    }
    public void criarActivityLogin(){
        Intent intent = new Intent(this, carteira.class);
        startActivity(intent);
    }
    /*private void criarActivityCadastro(){}*/
}
