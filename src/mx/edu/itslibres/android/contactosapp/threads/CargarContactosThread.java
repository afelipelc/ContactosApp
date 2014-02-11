package mx.edu.itslibres.android.contactosapp.threads;

import java.util.List;

import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;

import android.os.AsyncTask;

public final class CargarContactosThread extends AsyncTask<Void, Void, List<Contacto>> {
	
	AccesoContactosWS accesoContactosWS = new AccesoContactosWS();

	@Override
	protected final List<Contacto> doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		//return null;
		return accesoContactosWS.ListaContactos();
	}	
}
