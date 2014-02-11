package mx.edu.itslibres.android.contactosapp.dataaccess;

import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public final class AccesoServiciosWeb {
	//Método que invocará al WS y devolverá respuesta como objeto soap
	public SoapObject AccesoServicio(String EspacioNombres, String Metodo, String URL, Map<String, String> parametros)
	{
		//variables necesarias para la conexión y procesamiento de datos
		String soap_accion = EspacioNombres +"#"+Metodo;
		SoapObject request;
		SoapSerializationEnvelope envelope;
		//SoapObject  resultsRequestSOAP=null;
		request = new SoapObject(EspacioNombres, Metodo);
		
		Log.d("Llamando a servicio:", soap_accion);
		Log.d("Url Servicio: ", URL);

		//antes de iniciar la llamada, agregar los parametros (solo si se especificaron)
		//estos parametros corresponderan a los solicitados por el metodo que sea invocado
		if(parametros != null){
            for (Entry<String, String> parametro : parametros.entrySet()) {
                request.addProperty(parametro.getKey(), parametro.getValue());                
                Log.d("Parámetro enviado>>", parametro.getKey() + ": valor=" + parametro.getValue());
            }
        }
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
		envelope.dotNet = false; // no es un servicio .Net
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
	            transporte.call(soap_accion, envelope);
	            Log.d("Respuesta recibida>> ", envelope.bodyIn.toString());
	            
	            SoapObject result = (SoapObject) envelope.bodyIn;
	            
	            //devolvemos el objeto SOAP
	            return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
}
