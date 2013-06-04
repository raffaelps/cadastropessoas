package com.fumec.cadastrando;

import java.util.ArrayList;

import com.fumec.adapter.PessoaAdapter;
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
		
		this.preencherListaPessoas();
	}
	
	protected void preencherListaPessoas()
	{
		PessoaDTO pessoa1 = new PessoaDTO();
		pessoa1.setNomePessoa("Raffael Patrício");
		pessoa1.setTelefonePessoa("31 86551022");
		
		listaPessoas = new ArrayList<PessoaDTO>();
		listaPessoas.add(pessoa1);

        this.pessoaAdapter = new PessoaAdapter(this, R.layout.layout_celula_pessoa, listaPessoas);
        objListaPessoas.setAdapter(this.pessoaAdapter);
        
        objListaPessoas.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
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
