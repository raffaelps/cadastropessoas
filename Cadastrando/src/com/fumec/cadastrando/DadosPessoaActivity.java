package com.fumec.cadastrando;

import java.io.IOException;
import java.util.List;

import com.fumec.dal.SQLiteManager;
import com.fumec.modelo.PessoaDTO;
import com.fumec.util.Util;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class DadosPessoaActivity extends android.support.v4.app.FragmentActivity {

	private PessoaDTO pessoa;
	private LatLng latLngPessoa;
	private GoogleMap map;
	
	private List<Address> address;
	
	/**
	* Codigo de requisicao para poder identificar quando a activity da camera e finalizada
	 */
	@SuppressWarnings("unused")
	private static final int REQUEST_PICTURE = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_pessoa);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Bundle extras = getIntent().getExtras();
	    if (extras != null)
	    {
	        String idPessoa = extras.getString("idPessoa");
	        
	        SQLiteManager entry = new SQLiteManager(DadosPessoaActivity.this);
	        entry.open();
	        pessoa = entry.getPessoa(Integer.parseInt(idPessoa));
	        entry.close();
	        
	        if (pessoa.getNomePessoa() != null)
	        {
		        DadosPessoaFragment fragment = (DadosPessoaFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDadosPessoa);
		        
		        fragment.setDadosPessoa(pessoa);
		        
		        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		        
		        try {
		        	
		        	Geocoder coder = new Geocoder(this);
		        	Address location = new Address(null);
		        	
		        	if (pessoa.getLatitude() != null && pessoa.getLongitude() != null)
		        	{
		        		this.latLngPessoa = new LatLng(Double.parseDouble(pessoa.getLatitude()),Double.parseDouble(pessoa.getLongitude()));
		        	}
		        	else
		        	{
		        		address = coder.getFromLocationName(pessoa.getEnderecoPessoa() + "," + pessoa.getNumeroPessoa() + "," + pessoa.getBairroPessoa() + "," + pessoa.getCidadePessoa(),1);
		        		
		        		if (address != null && !address.isEmpty()) {
			        		location = address.get(0);
				            pessoa.setLatitude(String.valueOf(location.getLatitude()));
				            pessoa.setLongitude(String.valueOf(location.getLongitude()));
				            
				            entry.open();
				            @SuppressWarnings("unused")
							long retorno = entry.updateEntryPessoa(pessoa);
				            entry.close();
				            
				            this.latLngPessoa = new LatLng(location.getLatitude(),location.getLongitude());
		        		}
		        	}
		        	
		        	if (this.latLngPessoa != null)
		        	{
			            @SuppressWarnings("unused")
						Marker hamburg = map.addMarker(new MarkerOptions().position(latLngPessoa).title(pessoa.getNomePessoa()));
			            
			            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngPessoa, 15));
			        	map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		        	}
		        	else
		        	{
		        		Toast.makeText(this,"Não foi possível localizar o endereço.", Toast.LENGTH_SHORT).show();
		        	}

		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        finally
		        {
		        	
		        }
	        }
	        else
	        {
	        	super.onBackPressed();
	        }
	    }
	    else
	    {
	    	super.onBackPressed();
	    }
	    
	}
	
	/**
	 * Metodo que tira uma foto
	 * @param v
	 */
	public void cadastrarFoto(View v) 
	{
		DadosPessoaFragment fragment = (DadosPessoaFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDadosPessoa);
        fragment.cadastrarFoto(v);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		int opcao = item.getItemId();
		
		switch (opcao){
		case R.id.editar_pessoa:
			Intent intent = new Intent(this,EditarPessoaActivity.class);
			intent.putExtra("idPessoa", Integer.toString(pessoa.getIdPessoa()));
			startActivity(intent);
			break;
		case R.id.exportar_pessoa:
			
			if (Util.exportarPessoaJson(pessoa))
			{
				Toast.makeText(getApplicationContext(), "Pessoa exportada com sucesso.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Erro ao exportar pessoa.", Toast.LENGTH_SHORT).show();
			}
			
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
		
	}

}
