package com.example.carteira_vacinacao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CadastroDAO extends SQLiteOpenHelper {

    private static String DATABASE = "carteiraVacinacao";
    private static int VERSAO =1;

    public CadastroDAO(Context context){
        super(context,DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = ("CREATE TABLE usuario (\n" +
                "    id             INTEGER       PRIMARY KEY\n" +
                "                                 UNIQUE\n" +
                "                                 NOT NULL,\n" +
                "    nome           VARCHAR (100) NOT NULL,\n" +
                "    datanascimento DATE          NOT NULL,\n" +
                "    cpf            VARCHAR (11)  UNIQUE\n" +
                "                                 NOT NULL,\n" +
                "    email          VARCHAR (50)  NOT NULL\n" +
                "                                 UNIQUE,\n" +
                "    celular        VARCHAR (9)   NOT NULL,\n" +
                "    endereco       VARCHAR (100) NOT NULL,\n" +
                "    senha          VARCHAR (20)  NOT NULL\n" +
                ");");

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ddl = "DROP TABLE IF EXISTS usuario";
        db.execSQL(ddl);
        onCreate(db);
    }
}
