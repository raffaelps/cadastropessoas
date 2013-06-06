package com.fumec.cadastrando;

import com.fumec.modelo.PessoaDTO;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DadosPessoaFragment extends Fragment {

	private TextView txvNome;
	private TextView txvEndere�o;
	private TextView txvTelefone;
	private ImageView imagemPessoa;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dados_pessoa, container, false);
		
		txvNome = (TextView) view.findViewById(R.id.txvNome);
		txvEndere�o = (TextView) view.findViewById(R.id.txvEndereco);
		txvTelefone = (TextView) view.findViewById(R.id.txvTelefone);
		imagemPessoa = (ImageView) view.findViewById(R.id.imagemPessoa);
		
		return view;
	}

	public void setDadosPessoa(PessoaDTO pessoa) {
		txvNome.setText(pessoa.getNomePessoa());
		txvEndere�o.setText(pessoa.getEnderecoPessoa());
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
}
