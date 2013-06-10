package com.fumec.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

import com.fumec.modelo.PessoaDTO;
import com.google.gson.Gson;

public class Util {

	public static boolean exportarPessoaJson(PessoaDTO pessoa)
	{
		Gson g = new Gson();
		Log.i("JSON: ", g.toJson(pessoa));
		
		return generateFileOnSD(pessoa.getNomePessoa() + ".txt",g.toJson(pessoa));
	}
	
	public static boolean exportarPessoasJson(ArrayList<PessoaDTO> pessoas)
	{
		Gson g = new Gson();
		Log.i("JSON: ", g.toJson(pessoas));
		
		return generateFileOnSD("Pessoas.txt",g.toJson(pessoas));
	}
	
	public static boolean generateFileOnSD(String sFileName, String sBody){
		try
		{
			File root = new File(Environment.getExternalStorageDirectory(), "Cadastrando");
			
			if (!root.exists()) {
				root.mkdirs();
			}
			
			File gpxfile = new File(root, sFileName);
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(sBody);
			writer.flush();
			writer.close();
			
			return true;
		}
		catch(IOException e)
		{
			Log.i("Error: ",e.getMessage());
			
			return false;
		}
	}
}
