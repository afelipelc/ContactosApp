package mx.edu.itslibres.android.contactosapp.activities;

import java.util.ArrayList;
import java.util.List;
import mx.edu.itslibres.android.contactosapp.R;
import mx.edu.itslibres.android.contactosapp.adapters.ContactoAdapter;
import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import mx.edu.itslibres.android.contactosapp.threads.CargarContactosThread;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//declaramos nuestros objetos View
	ListView contactoslstw;
	ImageButton AgregarContactoBtn;
	ListView ContactosListView;
	
	AccesoContactosWS accesoContactosWS;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //inicializamos nuestro objeto contactoslstw
        this.contactoslstw = (ListView) findViewById(R.id.ListaContactosLst);
        this.AgregarContactoBtn = (ImageButton) findViewById(R.id.AgregarCImgb);
        this.ContactosListView = (ListView) findViewById(R.id.ListaContactosLst);
        /*
        //creamos una lista temporal que es un arreglo de cadenas
      
    	String[] milista = {
		"Elemento 1",
		"Elemento 2",
		"Elemento 3",
		"Elemento 4",
		"Elemento 5",
		"Elemento 6",
		"Elemento 7",
		"Elemento 8",
		"Elemento 9",
		"Elemento 10"
		};
        //mandamos a inflar el listview
    	this.contactoslstw.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,milista));
    	*/
        
        
        //creamos una lista temporal de objetos de la clase Contacto, para
        //mostrarlos como prueba a la interfaz
        
        /*
        List<Contacto> contactos = new ArrayList();
        
        //llenaremos la lista temporal con 8 objetos Contacto
        for(int i=1; i<=8; i++)
        {
        	Contacto contacto = new Contacto();
        	contacto.setId(i);
        	contacto.setNombre("Contacto ");
        	contacto.setApellidos(" x" + i);
        	contacto.setEmail("email"+i+"@ejemplo.com");
        	contacto.setTelefono(i+""+i+""+i);
        	
        	//agregamos el objeto a la lista de contactos
        	contactos.add(contacto);
        }
        */
        
        //llamamos al nuevo adaptador con la lista de objetos
        //this.contactoslstw.setAdapter(new ContactoAdapter(this, contactos));
        
        /*
        try{
        	accesoContactosWS = new AccesoContactosWS();
        	this.contactoslstw.setAdapter(new ContactoAdapter(this, accesoContactosWS.ListaContactos()));
        }catch(Exception e)
		{
			Toast.makeText(this, "Ocurrió un error al cargar la lista de contactos.", Toast.LENGTH_SHORT).show();
            //finish();
		}
        */
        
        //implementamos el evento clic para AgregarContactoBtn
        this.AgregarContactoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// hacer que abra el nuevo layout
				Intent agregarcontacto = new Intent(MainActivity.this, AgregarContactoActivity.class);
				startActivity(agregarcontacto);
			}
		});
        
        this.ContactosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				//aqui se recuperaran los datos del elemento
				//recuperamos solo el ID
				TextView idcontacto = (TextView)view.findViewById(R.id.IdContactoTxtw);
				
				//creamos el objeto que ayudara a llevar los datos
				Bundle datosextras = new Bundle();
				datosextras.putInt("Id", Integer.valueOf(idcontacto.getText().toString()));
				
				//pasamos a mostrar el activity de los detalles
				Intent detalles = new Intent(MainActivity.this, DetallesContactoActivity.class);
				//antes de llamar al intent, agregamos los extras
				detalles.putExtras(datosextras);
				
				startActivity(detalles);
				
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected void onResume() {
		
		// TODO Auto-generated method stub
		super.onResume();
		
		//Toast.makeText(getApplicationContext(), "Regresaste a mi",Toast.LENGTH_SHORT).show();
		try{
        	accesoContactosWS = new AccesoContactosWS();
        	//this.contactoslstw.setAdapter(new ContactoAdapter(this, accesoContactosWS.ListaContactos()));
        	
        	//crear una clase AsyncTask para permitir la carga en 2do plano
        	this.contactoslstw.setAdapter(new ContactoAdapter(this, new CargarContactosThread().execute().get()));
        }catch(Exception e)
		{
			Toast.makeText(this, "Ocurrió un error al cargar la lista de contactos.", Toast.LENGTH_SHORT).show();
            finish();
		}
	}
    
    
}
