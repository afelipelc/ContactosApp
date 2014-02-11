package mx.edu.itslibres.android.contactosapp.adapters;

import java.util.List;

import mx.edu.itslibres.android.contactosapp.R;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactoAdapter extends ArrayAdapter<Contacto> {

	Context context;
	List<Contacto> contactos;
	//Agregamos el constructor que pida uno de los parametros como List
	//para no trabajar directo con los arrays[]
	public ContactoAdapter(Context context, List<Contacto> contactos) {
		
		super(context, R.layout.list_item_contacto, contactos);
		//inicializamos el adaptador, donde definimos el layout a utilizar
		//para representar cada uno de los elementos de la lista
		
		Log.d("Contactos recibidos en adapter", String.valueOf(contactos.size()));
		this.context = context;
		this.contactos = contactos;
	}
	
	//implementamos el metodo getView() de la super clase
	//que servira para dar forma a cada uno de los elementos
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//creamos el objeto que inflara cada elemento
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//creamos el elementos de la lista
		View item = inflater.inflate(R.layout.list_item_contacto, null);
		
		//mandamos a asignar los datos en cada textview del elemento
		TextView nombre = (TextView) item.findViewById(R.id.NombreContactoTxtw);
		nombre.setText(contactos.get(position).getNombre()+" "+ contactos.get(position).getApellidos());
		
		TextView idcontacto = (TextView) item.findViewById(R.id.IdContactoTxtw);
		idcontacto.setText(String.valueOf(contactos.get(position).getId()));
		
		TextView email =  (TextView) item.findViewById(R.id.EmailTxtw);
		email.setText(contactos.get(position).getEmail());
		return item;
	}
}
