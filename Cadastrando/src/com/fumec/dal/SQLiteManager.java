package com.fumec.dal;

import java.util.ArrayList;

import com.fumec.modelo.PessoaDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager {

	public static final String _ROWID  = "id";
    public static final String _NOME    = "nome";
    public static final String _ENDERECO = "endereco";
    public static final String _TELEFONE = "telefone";
    public static final String _NUMERO = "numero";
    public static final String _BAIRRO = "bairro";
    public static final String _CIDADE = "cidade";
    public static final String _CEP = "cep";
    public static final String _NOMEFOTO = "nomefoto";
    public static final String _LATITUDE = "latitude";
    public static final String _LONGITUDE = "longitude";
 
    public static final String BD_NOME  = "Pessoas";
    public static final String BD_TABLE = "tb_pessoa";
    public static final int BD_VERSION  = 1;
 
    private DbHelper nAjuda;
    private final Context nContext;
    private SQLiteDatabase nBaseDados;
 
    private static class DbHelper extends SQLiteOpenHelper{
 
        public DbHelper(Context context){
            super(context, BD_NOME, null, BD_VERSION);
            // TODO Auto-generated method stub
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(
                    "CREATE TABLE " + BD_TABLE + " (" +
                    _ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    _NOME  + " TEXT NOT NULL, " +
                    _ENDERECO +	" TEXT NOT NULL," +
                    _TELEFONE +	" TEXT NOT NULL," +
                    _NUMERO + " TEXT NOT NULL," +
                    _BAIRRO + " TEXT NOT NULL," +
                    _CIDADE + " TEXT NOT NULL," +
                    _CEP + " TEXT NOT NULL," +
                    _NOMEFOTO + " TEXT NULL," +
                    _LATITUDE + " TEXT NULL," +
                    _LONGITUDE + " TEXT NULL);"
                    );
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVesion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + BD_TABLE);
            onCreate(db);
        }
 
    }
 
    public SQLiteManager(Context c){
        nContext = c;
    }
 
    public SQLiteManager open() throws SQLException{
        nAjuda = new DbHelper(nContext);
        nBaseDados = nAjuda.getWritableDatabase();
        return this;
    }
 
    public void close(){
        nAjuda.close();
    }
 
    public long createEntryPessoa(PessoaDTO pessoa) {
        ContentValues cv = new ContentValues();
        cv.put(_NOME, pessoa.getNomePessoa());
        cv.put(_ENDERECO, pessoa.getEnderecoPessoa());
        cv.put(_TELEFONE, pessoa.getTelefonePessoa());
        cv.put(_NUMERO, pessoa.getNumeroPessoa());
        cv.put(_BAIRRO, pessoa.getBairroPessoa());
        cv.put(_CIDADE, pessoa.getCidadePessoa());
        cv.put(_CEP, pessoa.getCepPessoa());
        cv.put(_NOMEFOTO, pessoa.getFotoPessoa());
        cv.put(_LATITUDE, pessoa.getLatitude());
        cv.put(_LONGITUDE, pessoa.getLongitude());
        
        return nBaseDados.insert(BD_TABLE, null, cv);
    }
    
    public long updateEntryPessoa(PessoaDTO pessoa) {
        ContentValues cv = new ContentValues();
        cv.put(_NOME, pessoa.getNomePessoa());
        cv.put(_ENDERECO, pessoa.getEnderecoPessoa());
        cv.put(_TELEFONE, pessoa.getTelefonePessoa());
        cv.put(_NUMERO, pessoa.getNumeroPessoa());
        cv.put(_BAIRRO, pessoa.getBairroPessoa());
        cv.put(_CIDADE, pessoa.getCidadePessoa());
        cv.put(_CEP, pessoa.getCepPessoa());
        cv.put(_NOMEFOTO, pessoa.getFotoPessoa());
        cv.put(_LATITUDE, pessoa.getLatitude());
        cv.put(_LONGITUDE, pessoa.getLongitude());
        
        return nBaseDados.update(BD_TABLE, cv, _ROWID + " = " + pessoa.getIdPessoa(), null);
    }
    
    public long deleteEntryPessoa(PessoaDTO pessoa) {
        return nBaseDados.delete(BD_TABLE, _ROWID + " = " + pessoa.getIdPessoa(),null);
    }
 
    public ArrayList<PessoaDTO> getPessoas() {
        // TODO Auto-generated method stub
        String[] colunas = new String[]{ _ROWID, _NOME, _ENDERECO, _TELEFONE, _NUMERO, _BAIRRO, _CIDADE , _CEP, _NOMEFOTO, _LATITUDE, _LONGITUDE };
        Cursor c = nBaseDados.query(BD_TABLE, colunas, null, null, null, null, _NOME + " ASC");
 
        ArrayList<PessoaDTO> retorno = new ArrayList<PessoaDTO>();
        
        PessoaDTO pessoa;
 
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
        	
        	pessoa = new PessoaDTO();
        	
            pessoa.setIdPessoa(c.getInt(c.getColumnIndex(_ROWID)));
            pessoa.setNomePessoa(c.getString(c.getColumnIndex(_NOME)));
            pessoa.setEnderecoPessoa(c.getString(c.getColumnIndex(_ENDERECO)));
            pessoa.setTelefonePessoa(c.getString(c.getColumnIndex(_TELEFONE)));
            pessoa.setNumeroPessoa(c.getString(c.getColumnIndex(_NUMERO)));
            pessoa.setBairroPessoa(c.getString(c.getColumnIndex(_BAIRRO)));
            pessoa.setCidadePessoa(c.getString(c.getColumnIndex(_CIDADE)));
            pessoa.setCepPessoa(c.getString(c.getColumnIndex(_CEP)));
            pessoa.setFotoPessoa(c.getString(c.getColumnIndex(_NOMEFOTO)));
            pessoa.setLatitude(c.getString(c.getColumnIndex(_LATITUDE)));
            pessoa.setLongitude(c.getString(c.getColumnIndex(_LONGITUDE)));
            
            //Adiciona pessoa
            retorno.add(pessoa);
        }
        
        return retorno;
    }
    
    public PessoaDTO getPessoa(int idPessoa)
    {
    	// TODO Auto-generated method stub
        String[] colunas = new String[]{ _ROWID, _NOME, _ENDERECO, _TELEFONE, _NUMERO, _BAIRRO, _CIDADE , _CEP, _NOMEFOTO, _LATITUDE, _LONGITUDE };
        Cursor c = nBaseDados.query(BD_TABLE, colunas, _ROWID + " = " + idPessoa, null, null, null, _NOME + " ASC");
 
        PessoaDTO pessoa = new PessoaDTO();
 
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
        	
            pessoa.setIdPessoa(c.getInt(c.getColumnIndex(_ROWID)));
            pessoa.setNomePessoa(c.getString(c.getColumnIndex(_NOME)));
            pessoa.setEnderecoPessoa(c.getString(c.getColumnIndex(_ENDERECO)));
            pessoa.setTelefonePessoa(c.getString(c.getColumnIndex(_TELEFONE)));
            pessoa.setNumeroPessoa(c.getString(c.getColumnIndex(_NUMERO)));
            pessoa.setBairroPessoa(c.getString(c.getColumnIndex(_BAIRRO)));
            pessoa.setCidadePessoa(c.getString(c.getColumnIndex(_CIDADE)));
            pessoa.setCepPessoa(c.getString(c.getColumnIndex(_CEP)));
            pessoa.setFotoPessoa(c.getString(c.getColumnIndex(_NOMEFOTO)));
            pessoa.setLatitude(c.getString(c.getColumnIndex(_LATITUDE)));
            pessoa.setLongitude(c.getString(c.getColumnIndex(_LONGITUDE)));

        }
        
        return pessoa;
    }
}
