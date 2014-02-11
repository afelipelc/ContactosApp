package mx.edu.itslibres.android.contactosapp.dataaccess;

import java.util.ArrayList;
import java.util.List;

import mx.edu.itslibres.android.contactosapp.models.Contacto;

import org.ksoap2.serialization.SoapObject;

/*
 * Clase que convierte un vector de objetos SOAP a list<Contacto>
 * Tambien convierte un solo objeto SOAP a objeto Contacto
 */
public final class ConvertContacto {
	public List<Contacto> ListaContactos(List<SoapObject> lista){

		List<Contacto> contactos = new ArrayList<Contacto>();
		for(SoapObject item : lista)
		{
			/*
			Contacto contacto = new Contacto();
			contacto.setId(Integer.parseInt(item.getProperty("id").toString()));
			contacto.setNombre(item.getProperty("nombre").toString());
			contacto.setApellidos(item.getProperty("apellidos").toString());
			contacto.setEmail(item.getProperty("email").toString());
			contacto.setTelefono(item.getProperty("telefono").toString());
			
			*/
			//llamamos al metodo que procesa un solo objeto soap
			//y lo agregamos a la lista de contactos
			contactos.add(UnicoContacto(item));
			//contactos.add(contacto);
		}
		
		return contactos;
	}
	
	public Contacto UnicoContacto(SoapObject item){
		
		Contacto contacto = new Contacto();
		contacto.setId(Integer.parseInt(item.getProperty("id").toString()));
		contacto.setNombre(item.getProperty("nombre").toString());
		contacto.setApellidos(item.getProperty("apellidos").toString());
		contacto.setEmail(item.getProperty("email").toString());
		contacto.setTelefono(item.getProperty("telefono").toString());
		
		return contacto;
	}
}
