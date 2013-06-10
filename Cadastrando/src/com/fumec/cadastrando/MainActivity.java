package com.fumec.cadastrando;

import java.util.ArrayList;

import com.fumec.adapter.PessoaAdapter;
import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;
import com.fumec.util.Util;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ListView objListaPessoas;
	private ArrayList<PessoaDTO> listaPessoas;
	private PessoaAdapter pessoaAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		objListaPessoas = (ListView) findViewById(R.id.lstPessoas);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.preencherListaPessoas();
	}
	
	protected void preencherListaPessoas()
	{
		SQLiteManager info = new SQLiteManager(this);
		 
        info.open();
        listaPessoas = info.getPessoas();
        info.close();

        this.pessoaAdapter = new PessoaAdapter(this, R.layout.layout_celula_pessoa, listaPessoas);
        objListaPessoas.setAdapter(this.pessoaAdapter);
        
        objListaPessoas.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				
				PessoaDTO pessoa = listaPessoas.get(posicao);
				
				Intent intent = new Intent(MainActivity.this, DadosPessoaActivity.class);
				intent.putExtra("idPessoa", Integer.toString(pessoa.getIdPessoa()));
				startActivity(intent);
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		int opcao = item.getItemId();
		
		switch (opcao){
		case R.id.cadastrar_pessoa:
			Intent intent_login = new Intent(this,CadastroPessoaActivity.class);
			startActivity(intent_login);
			break;
		case R.id.exportar_pessoas:
			
			if (Util.exportarPessoasJson(listaPessoas))
			{
				Toast.makeText(getApplicationContext(), "Pessoas exportadas com sucesso.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Erro ao exportar pessoas.", Toast.LENGTH_SHORT).show();
			}
			
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
		
	}

}
