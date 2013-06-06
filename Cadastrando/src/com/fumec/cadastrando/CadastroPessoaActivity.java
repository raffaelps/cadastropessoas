package com.fumec.cadastrando;

import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CadastroPessoaActivity  extends Activity {

	private EditText edtNome;
	private EditText edtTelefone;
	private EditText edtEndereco;
	private EditText edtNumero;
	private EditText edtCidade;
	private EditText edtBairro;
	private EditText edtCep;
	
	private ImageView imagemPessoa;
	
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
		
		imagemPessoa = (ImageView) findViewById(R.id.imagemPessoa);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void cadastrarPessoa(View view)
	{
		ProgressDialog dialog = ProgressDialog.show(CadastroPessoaActivity.this, "", 
                "Gravando dados...", true);
		
		PessoaDTO pessoa = new PessoaDTO();
		pessoa.setNomePessoa(edtNome.getText().toString());
		pessoa.setTelefonePessoa(edtTelefone.getText().toString());
		pessoa.setEnderecoPessoa(edtEndereco.getText().toString());
		pessoa.setNumeroPessoa(edtNumero.getText().toString());
		pessoa.setCidadePessoa(edtCidade.getText().toString());
		pessoa.setBairroPessoa(edtBairro.getText().toString());
		pessoa.setCepPessoa(edtCep.getText().toString());
		//pessoa.setFotoPessoa(imagemPessoa.)
		//pessoa.setFotoPessoa("foto.png");
		//pessoa.setLongitude("123465");
		//pessoa.setLatitude("132465");
		
		SQLiteManager entry = new SQLiteManager(CadastroPessoaActivity.this);
        entry.open();
        long retorno = entry.createEntryPessoa(pessoa);
        entry.close();
        
        dialog.dismiss();
        
        if (retorno == -1) 
        {
        	Toast.makeText(getApplicationContext(), "Erro ao cadastrar pessoa.", Toast.LENGTH_SHORT).show();
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Pessoa cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
        }
	}
	
	public void cadastrarFoto (View view)
	{
		
	}
}
