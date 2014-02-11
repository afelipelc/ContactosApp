package mx.edu.itslibres.android.contactosapp.threads;

import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import android.os.AsyncTask;

public final class CargarContactoThread extends AsyncTask<Integer, Void, Contacto> {
	
	AccesoContactosWS accesoContactosWS = new AccesoContactosWS();

	@Override
	protected final Contacto doInBackground(Integer... arg0) {
		// TODO Auto-generated method stub
		return accesoContactosWS.DatosContacto(arg0[0]);
	}
}
