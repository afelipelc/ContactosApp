package mx.edu.itslibres.android.contactosapp.dataaccess;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.ksoap2.serialization.SoapObject;

import android.util.Log;

import mx.edu.itslibres.android.contactosapp.models.Contacto;


public final class AccesoContactosWS {
	//creamos una instancia de nuestra clase para acceder a los WS
	AccesoServiciosWeb acesoservicio = new AccesoServiciosWeb();
	
	//nuestra variable que almacene la url del WS
	private  String URL_WebServices = "http://172.16.1.171/ws/servidor.php";
	//nombre del espacio de nombres del web service (es la misma url)
	private String EspacioNombres = "http://172.16.1.171/ws/servidor.php";
	
	
	//metodo que devuelva la lista de contactos obtenida desde el WS
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final List<Contacto> ListaContactos(){
		try {
			//acceder al servicio
			
			SoapObject resultado = acesoservicio.AccesoServicio(EspacioNombres, "Lista", URL_WebServices, null);
			
			//coleccion que almacenara la lista de contactos
			//la inicializamos como null para devolverla asi en caso
			//de que no se obtenga ningun resultado
			List<Contacto> listac = null;

			if(resultado.getProperty(1).getClass() == java.util.Vector.class)
			{
				//Log.d("Empezando conversion", "se recibio vector");
				//obtenemos la lista de contactos desde el WS y la convertimos a List<Contacto>
				listac = new ConvertContacto().ListaContactos(Collections.list((Enumeration<SoapObject>)((Vector)resultado.getProperty(1)).elements()));
				//Log.d("Total procesados", String.valueOf(listac.size()));
				
			}
			
			//devolver la lista de contactos ya procesada
			return listac;
			
		}catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	//metodo que obtenga los datos de un contacto
	public final Contacto DatosContacto(int idContacto){
		try {
			Contacto contacto = null;
			//crear los parametros solicitados por el metodo a llamar
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("idContacto", String.valueOf(idContacto));
			
			//obtenemos el objeto soap que devolvio el WS
			//aqui si le pasamos los parametros
			SoapObject resultado = acesoservicio.AccesoServicio(EspacioNombres, "DatosContacto", URL_WebServices, parametros);
			
			//procesamos el objeto
			if(resultado.getProperty(1).getClass() == SoapObject.class)
			{
				contacto = new ConvertContacto().UnicoContacto((SoapObject)resultado.getProperty(1));
			}
			
			//devolvemos el contacto
			return contacto;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	//metodo que llama al registro de un nuevo contacto
	public final Contacto RegistrarContacto(Contacto contacto){
		try {			
			//crear los parametros solicitados por el metodo a llamar
			//aqui seran todos los datos de un contacto
			Map<String, String> parametros = new HashMap<String, String>();
			parametros.put("nombre", contacto.getNombre());
            parametros.put("apellidos", contacto.getApellidos());
            parametros.put("email", contacto.getEmail());
            parametros.put("telefono", contacto.getTelefono());
			//obtenemos el objeto soap que devolvio el WS
			//aqui si le pasamos los parametros
			SoapObject resultado = acesoservicio.AccesoServicio(EspacioNombres, "RegistrarContacto", URL_WebServices, parametros);
			
			//procesamos el objeto
			if(resultado.getProperty(1).getClass() == SoapObject.class)
			{
				contacto = new ConvertContacto().UnicoContacto((SoapObject)resultado.getProperty(1));
			}
			
			//devolvemos el contacto
			return contacto;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	//metodo que llama a la actualizacion de los datos de un nuevo contacto
		public final boolean ActualizarContacto(Contacto contacto){
			try {			
				//crear los parametros solicitados por el metodo a llamar
				//aqui seran todos los datos de un contacto
				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("id", String.valueOf(contacto.getId()));
				parametros.put("nombre", contacto.getNombre());
	            parametros.put("apellidos", contacto.getApellidos());
	            parametros.put("email", contacto.getEmail());
	            parametros.put("telefono", contacto.getTelefono());
				//String contactovector = "Contacto{id=1; nomb=A. Felipe; apellidos=Lim; email=afelipelc@gmail.com; telefono=2222222;}";
				//parametros.put("contacto", contactovector);
				//obtenemos el objeto soap que devolvio el WS
				//aqui si le pasamos los parametros
				SoapObject resultado = acesoservicio.AccesoServicio(EspacioNombres, "ActualizarContacto", URL_WebServices, parametros);
				
				//procesamos el objeto
				/*
				if(resultado.getProperty(1).getClass() == SoapObject.class)
				{
					contacto = new ConvertContacto().UnicoContacto((SoapObject)resultado.getProperty(1));
				}
				*/
				Log.d("Recibido>>", String.valueOf(resultado.getProperty(1))+String.valueOf(resultado.getProperty(1).getClass()));
				
				
				//devolvemos el resultado
				
				return Boolean.valueOf(String.valueOf(resultado.getProperty(1)));
				
			}catch (Exception e) {
				return false;
			}
		}
	
		
		//metodo que llama la busqueda de contactos
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public final List<Contacto> BuscarContactos(String q){
			try {
				
				//coleccion que almacenara la lista de contactos
				//la inicializamos como null para devolverla asi en caso
				//de que no se obtenga ningun resultado
				List<Contacto> listac = null;
				
				//crear los parametros solicitados por el metodo a llamar
				//aqui sera la cadena a buscar
				Map<String, String> parametros = new HashMap<String, String>();
				parametros.put("q", q);
				
				//acceder al servicio
				SoapObject resultado = acesoservicio.AccesoServicio(EspacioNombres, "Buscar", URL_WebServices, null);
				
				if(resultado.getProperty(1).getClass() == java.util.Vector.class)
				{
					//obtenemos la lista de contactos desde el WS y la convertimos a List<Contacto>
					listac = new ConvertContacto().ListaContactos(Collections.list((Enumeration<SoapObject>)((Vector)resultado.getProperty(1)).elements()));
				}
				
				//devolver la lista de contactos ya procesada
				return listac;
				
			}catch (Exception e) {
				//e.printStackTrace();
				return null;
			}
		}
}
