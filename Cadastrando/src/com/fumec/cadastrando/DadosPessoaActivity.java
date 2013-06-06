package com.fumec.cadastrando;

import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

@SuppressLint("NewApi")
public class DadosPessoaActivity extends Activity {

	private PessoaDTO pessoa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_pessoa);
		
		Bundle extras = getIntent().getExtras();
	    if (extras != null)
	    {
	        String idPessoa = extras.getString("idPessoa");
	        
	        SQLiteManager entry = new SQLiteManager(DadosPessoaActivity.this);
	        entry.open();
	        pessoa = entry.getPessoa(Integer.parseInt(idPessoa));
	        entry.close();
	        
	        DadosPessoaFragment fragment = (DadosPessoaFragment) getFragmentManager().findFragmentById(R.id.fragmentDadosPessoa);
	        fragment.setDadosPessoa(pessoa);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
