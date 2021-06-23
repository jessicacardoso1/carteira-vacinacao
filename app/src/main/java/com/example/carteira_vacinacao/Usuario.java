package com.example.carteira_vacinacao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.carteira_vacinacao.dao.CadastroDAO;

import java.util.ArrayList;

public class Usuario {


    private int codigo;
    private String nome;
    private String dataNascimento;
    private String cpf;
    private String email;
    private String celular;
    private String endereco;
    private String senha;
    private Context context;
    private boolean excluir;


    public Usuario(Context context){
        this.context = context;
        codigo = -1;
    }
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }
/*
    public boolean excluir(){
        CadastroDAO cadastroDAO = null;
        SQLiteDatabase sqLiteDatabase = null;

        try {
            cadastroDAO = new CadastroDAO(context);
            sqLiteDatabase = cadastroDAO.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("usuario","id = ?", new String[]{String.valueOf(codigo)});

            excluir = true;
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (cadastroDAO != null)
                cadastroDAO.close();
        }
    }*/

    public boolean salvar(){
        CadastroDAO cadastroDAO = null;
        SQLiteDatabase sqLiteDatabase = null;

        try {
            cadastroDAO = new CadastroDAO(context);
            sqLiteDatabase = cadastroDAO.getWritableDatabase();
            String sql = "";
            if(codigo == -1){
                sql = "INSERT INTO usuario (\n" +
                        "                        nome,\n" +
                        "                        datanascimento,\n" +
                        "                        cpf,\n" +
                        "                        email,\n" +
                        "                        celular,\n" +
                        "                        endereco,\n" +
                        "                        senha\n" +
                        "                    )\n" +
                        "                    VALUES (\n" +
                        "                        ?,\n" +
                        "                        ?,\n" +
                        "                        ?,\n" +
                        "                        ?,\n" +
                        "                        ?,\n" +
                        "                        ?,\n" +
                        "                        ?\n" +
                        "                    )";
            }else{
                sql = "UPDATE usuario\n" +
                        "   SET nome = ?,\n" +
                        "       datanascimento = ?,\n" +
                        "       cpf = ?,\n" +
                        "       email = ?,\n" +
                        "       celular = ?,\n" +
                        "       endereco = ?,\n" +
                        "       senha = ?\n" +
                        " WHERE id = ?\n";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1, nome);
            sqLiteStatement.bindString(2, dataNascimento);
            sqLiteStatement.bindString(3, cpf);
            sqLiteStatement.bindString(4, email);
            sqLiteStatement.bindString(5, celular);
            sqLiteStatement.bindString(6, endereco);
            sqLiteStatement.bindString(7, senha);
            if(codigo != -1)
                sqLiteStatement.bindString(8, String.valueOf(codigo));
            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (cadastroDAO != null)
                cadastroDAO.close();
        }
    }
/*
    public ArrayList<Usuario> getUsuarios(){
        CadastroDAO cadastroDAO = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<Usuario> Usuario= new ArrayList<>();

        try {
            cadastroDAO = new CadastroDAO(context);
            sqLiteDatabase = cadastroDAO.getReadableDatabase();
            cursor = sqLiteDatabase.query("usuario", null,null, null,null,null,null,);
            while (cursor.moveToNext()){
                Usuario usuario = new Usuario(context);
                usuario.codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
                usuario.nome = cursor.getString(cursor.getColumnIndex("nome"));
                usuario.dataNascimento = cursor.getString(cursor.getColumnIndex("datanascimento"));
                usuario.cpf = cursor.getString(cursor.getColumnIndex("cpf"));
                usuario.email=  cursor.getString(cursor.getColumnIndex("email"));
                usuario.celular = cursor.getString(cursor.getColumnIndex("celular"));
                usuario.endereco = cursor.getString(cursor.getColumnIndex("endereco"));
                usuario.senha = cursor.getString(cursor.getColumnIndex("senha"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (cadastroDAO != null)
                cadastroDAO.close();
        }
        return Usuario;
    }*/

   public void carregaUsuarioPeloCPF(String cpf){
        CadastroDAO cadastroDAO = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try {
            cadastroDAO = new CadastroDAO(context);
            sqLiteDatabase = cadastroDAO.getReadableDatabase();
            cursor = sqLiteDatabase.query("usuario",null, "cpf = ?", new String[] {String.valueOf(cpf)},null,null, null);
            while (cursor.moveToNext()){
                nome = cursor.getString(cursor.getColumnIndex("nome"));
                dataNascimento = cursor.getString(cursor.getColumnIndex("datanascimento"));
                this.cpf = cursor.getString(cursor.getColumnIndex("cpf"));
                email=  cursor.getString(cursor.getColumnIndex("email"));
                celular = cursor.getString(cursor.getColumnIndex("celular"));
                endereco = cursor.getString(cursor.getColumnIndex("endereco"));
                senha = cursor.getString(cursor.getColumnIndex("senha"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
            if (cadastroDAO != null)
                cadastroDAO.close();
        }

    }




}
