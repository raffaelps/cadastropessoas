package com.fumec.cadastrando;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DadosPessoaFragment extends Fragment {

	private TextView txvNome;
	private TextView txvEndereco;
	private TextView txvTelefone;
	private ImageView imagemPessoa;
	private String nomeImagemPessoa;
	
	private PessoaDTO pessoa;
	
	/**
	* Codigo de requisicao para poder identificar quando a activity da camera e finalizada
	 */
	private static final int REQUEST_PICTURE = 1000;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dados_pessoa, container, false);
		
		txvNome = (TextView) view.findViewById(R.id.txvNome);
		txvEndereco = (TextView) view.findViewById(R.id.txvEndereco);
		txvTelefone = (TextView) view.findViewById(R.id.txvTelefone);
		imagemPessoa = (ImageView) view.findViewById(R.id.imagemPessoa);
		
		return view;
	}

	public void setDadosPessoa(PessoaDTO pessoaDTO) {
		
		this.pessoa = pessoaDTO;
		
		txvNome.setText(pessoa.getNomePessoa());
		txvEndereco.setText(pessoa.getEnderecoPessoa() + ", " + pessoa.getNumeroPessoa() + ", " + pessoa.getBairroPessoa() + ", " + pessoa.getCepPessoa() + " - " + pessoa.getCidadePessoa());
		txvTelefone.setText(pessoa.getTelefonePessoa());
		
		if (pessoa.getFotoPessoa() != null)
		{
			imagemPessoa.setImageBitmap(BitmapFactory.decodeFile(pessoa.getFotoPessoa()));
		}
		else
    	{
			imagemPessoa.setImageResource(R.drawable.person);
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
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
			        
			        pessoa.setFotoPessoa(imgFile.getAbsolutePath());
			        
			        SQLiteManager entry = new SQLiteManager(this.getActivity());
			        entry.open();
			        long retorno = entry.updateEntryPessoa(pessoa);
			        entry.close();
			        
			        if (retorno == -1) 
			        {
			        	Toast.makeText(this.getActivity(), "Erro ao atualizar pessoa.", Toast.LENGTH_SHORT).show();
			        }
			        else
			        {
			        	Toast.makeText(this.getActivity(), "Pessoa atualizada com sucesso.", Toast.LENGTH_SHORT).show();
			        }
			        
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
