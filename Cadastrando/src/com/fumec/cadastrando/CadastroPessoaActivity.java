package com.fumec.cadastrando;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class CadastroPessoaActivity  extends Activity {

	private EditText edtNome;
	private EditText edtTelefone;
	private EditText edtEndereco;
	private EditText edtNumero;
	private EditText edtCidade;
	private EditText edtBairro;
	private EditText edtCep;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_pessoa);
		
		edtNome = (EditText) findViewById(R.id.edtNome);
		edtTelefone = (EditText) findViewById(R.id.edtTelefone);
		edtEndereco = (EditText) findViewById(R.id.edtEndereco);
		edtNumero = (EditText) findViewById(R.id.edtNumero);
		edtCidade = (EditText) findViewById(R.id.edtCidade);
		edtBairro = (EditText) findViewById(R.id.edtBairro);
		edtCep = (EditText) findViewById(R.id.edtCep);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
