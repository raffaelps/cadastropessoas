package com.fumec.cadastrando;


import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class CadastroPessoaActivity  extends Activity {

	private EditText edtNome;
	private EditText edtTelefone;
	private EditText edtEndereco;
	private EditText edtNumero;
	private EditText edtCidade;
	private EditText edtBairro;
	private EditText edtCep;
	
	private ImageView imagemPessoa;
	private String nomeImagemPessoa;
	
	/**
	* Codigo de requisicao para poder identificar quando a activity da camera e finalizada
	 */
	private static final int REQUEST_PICTURE = 1000;
	
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
		PessoaDTO pessoa = new PessoaDTO();
		pessoa.setNomePessoa(edtNome.getText().toString());
		pessoa.setTelefonePessoa(edtTelefone.getText().toString());
		pessoa.setEnderecoPessoa(edtEndereco.getText().toString());
		pessoa.setNumeroPessoa(edtNumero.getText().toString());
		pessoa.setCidadePessoa(edtCidade.getText().toString());
		pessoa.setBairroPessoa(edtBairro.getText().toString());
		pessoa.setCepPessoa(edtCep.getText().toString());
		pessoa.setFotoPessoa(nomeImagemPessoa);
		//pessoa.setLongitude("123465");
		//pessoa.setLatitude("132465");
		
		SQLiteManager entry = new SQLiteManager(CadastroPessoaActivity.this);
        entry.open();
        long retorno = entry.createEntryPessoa(pessoa);
        entry.close();
        
        if (retorno == -1) 
        {
        	Toast.makeText(getApplicationContext(), "Erro ao cadastrar pessoa.", Toast.LENGTH_SHORT).show();
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Pessoa cadastrada com sucesso.", Toast.LENGTH_SHORT).show();
        }
	}
	
	/**
	 * Metodo que tira uma foto
	 * @param v
	 */
	public void cadastrarFoto(View v) {
		
		// Cria uma intent que sera usada para abrir a aplicacao nativa de camera
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// Abre a aplicacao de camera
		startActivityForResult(i, REQUEST_PICTURE);
	}
	
	/**
	 * Metodo chamado quando a aplicacao nativa da camera e finalizada
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == REQUEST_PICTURE && resultCode == Activity.RESULT_OK) {
			
			
			FileInputStream fis = null;
			
			try {
				try {
					
					//Define o nome da imagem
					Date now = new Date();
					nomeImagemPessoa = Integer.toString(now.getDay()) + Integer.toString(now.getMonth()) + Integer.toString(now.getYear());
					nomeImagemPessoa += Integer.toString(now.getHours()) + Integer.toString(now.getMinutes()) + Integer.toString(now.getSeconds());
					nomeImagemPessoa += ".jpg";
					
					File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					File imgFile = new  File(dir.toString() + "/" + nomeImagemPessoa);	

					Bitmap image = (Bitmap) data.getExtras().get("data");
					
					FileOutputStream fOut = new FileOutputStream(imgFile);
					image.compress(Bitmap.CompressFormat.PNG, 50, fOut);
					fOut.flush();
					fOut.close();
					
			        imagemPessoa.setImageBitmap(image);
			        
			        nomeImagemPessoa = imgFile.getAbsolutePath();
			        
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
