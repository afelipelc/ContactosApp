package mx.edu.itslibres.android.contactosapp.threads;

import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import android.os.AsyncTask;

public final class GuardarContactoThread extends AsyncTask<Contacto, Void, Boolean> {

	AccesoContactosWS accesoContactosWS = new AccesoContactosWS();
	
	@Override
	protected final Boolean doInBackground(Contacto... arg0) {
		// TODO Auto-generated method stub
		return accesoContactosWS.ActualizarContacto(arg0[0]);
	}
}
