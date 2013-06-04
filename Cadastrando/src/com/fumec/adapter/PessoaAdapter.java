package com.fumec.adapter;

import java.util.ArrayList;

import com.fumec.cadastrando.R;
import com.fumec.modelo.PessoaDTO;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PessoaAdapter extends ArrayAdapter<PessoaDTO> {
	
	public ArrayList<PessoaDTO> listaPessoas;

	public PessoaAdapter(Context context, int textViewResourceId, ArrayList<PessoaDTO> lista) {
		super(context, textViewResourceId, lista);
		this.listaPessoas = lista;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.layout_celula_pessoa, null);
            }
            
            PessoaDTO o = listaPessoas.get(position);
            
            if (o != null) {
                    TextView tt = (TextView) v.findViewById(R.id.nomePessoa);
                    TextView bt = (TextView) v.findViewById(R.id.telefonePessoa);
                    ImageView img = (ImageView) v.findViewById(R.id.imagemPessoa);
                    
                    if (tt != null) {
                          tt.setText(o.getNomePessoa());
                    }
                    
                    if(bt != null){
                          bt.setText(o.getTelefonePessoa());
                    }
                    
                    if (img != null)
                    {
                    	if (o.getFotoPessoa() != "")
                    	{
                    		img.setImageBitmap(BitmapFactory.decodeFile(o.getFotoPessoa()));
                    	}
                    	else
                    	{
                    		//img.setImageResource(R.drawable.imageFileId);
                    	}
                    }
            }
            return v;
    }

}
