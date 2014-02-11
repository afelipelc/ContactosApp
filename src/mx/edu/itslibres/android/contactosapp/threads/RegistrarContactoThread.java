package mx.edu.itslibres.android.contactosapp.threads;

import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import android.os.AsyncTask;

public class RegistrarContactoThread extends AsyncTask<Contacto, Void, Contacto> {

	AccesoContactosWS accesoContactosWS = new AccesoContactosWS();
	
	@Override
	protected Contacto doInBackground(Contacto... arg0) {
		// TODO Auto-generated method stub
		return accesoContactosWS.RegistrarContacto(arg0[0]);
	}
}
