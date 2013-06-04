package com.fumec.cadastrando;

import java.util.ArrayList;

import com.fumec.adapter.PessoaAdapter;
import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
		
		PessoaDTO pessoa = new PessoaDTO();
		pessoa.setNomePessoa("Raffael Patrício");
		pessoa.setDataNascimentoPessoa("03/10/1986");
		pessoa.setTelefonePessoa("31 86551022");
		pessoa.setEnderecoPessoa("Rua");
		pessoa.setNumeroPessoa("325 Casa A");
		pessoa.setCidadePessoa("Belo Horizonte");
		pessoa.setBairroPessoa("Ipanema");
		pessoa.setCepPessoa("30870-010");
		pessoa.setFotoPessoa("foto.png");
		pessoa.setLongitude("123465");
		pessoa.setLatitude("132465");
		
		SQLiteManager entry = new SQLiteManager(MainActivity.this);
        entry.open();
        entry.createEntryPessoa(pessoa);
        entry.close();
		
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
				//String variavelNome = "Joãozinho";
				//Intent intent = new Intent(this, ActivitySecundaria.class);
				//intent.putExtra("nome", variavelNome);
				//startActivity(intent);
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
