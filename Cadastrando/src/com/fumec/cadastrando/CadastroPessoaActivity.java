package com.fumec.cadastrando;


import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
	private File imageFile;
	private String nomeImagemPessoa;
	
	/**
	* Código de requisição para poder identificar quando a activity da câmera é finalizada
	 */
	private static final int REQUEST_PICTURE = 1000;
	
	@SuppressWarnings("deprecation")
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
		
		// Obtém o local onde as fotos são armazenadas na memória externa do dispositivo
		File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		
		//Define o local completo onde a foto será armazenada (diretório + arquivo)
		Date now = new Date();
		nomeImagemPessoa = Integer.toString(now.getDay()) + Integer.toString(now.getMonth()) + Integer.toString(now.getYear());
		nomeImagemPessoa += Integer.toString(now.getHours()) + Integer.toString(now.getMinutes()) + Integer.toString(now.getSeconds());
		nomeImagemPessoa += ".jpg";
		
		this.imageFile = new File(picsDir, nomeImagemPessoa);
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
		pessoa.setFotoPessoa(nomeImagemPessoa);
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
	
	/**
	 * Método que tira uma foto
	 * @param v
	 */
	public void cadastrarFoto(View v) {
		// Cria uma intent que será usada para abrir a aplicação nativa de câmera
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		// Indica na intent o local onde a foto tirada deve ser armazenada
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		
		// Abre a aplicação de câmera
		startActivityForResult(i, REQUEST_PICTURE);
	}
	
	/**
	 * Método chamado quando a aplicação nativa da câmera é finalizada
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// Verifica o código de requisição e se o resultado é OK (outro resultado indica que
		// o usuário cancelou a tirada da foto)
		
		if (requestCode == REQUEST_PICTURE && resultCode == RESULT_OK) {
			
			FileInputStream fis = null;
			
			try {
				try {
					// Cria um FileInputStream para ler a foto tirada pela câmera
					fis = new FileInputStream(imageFile);
					
					// Converte a stream em um objeto Bitmap
					Bitmap picture = BitmapFactory.decodeStream(fis);
					
					// Exibe o bitmap na view, para que o usuário veja a foto tirada
					imagemPessoa.setImageBitmap(picture);
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
