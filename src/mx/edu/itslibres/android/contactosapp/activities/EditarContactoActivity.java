package mx.edu.itslibres.android.contactosapp.activities;

import mx.edu.itslibres.android.contactosapp.R;
import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import mx.edu.itslibres.android.contactosapp.threads.CargarContactoThread;
import mx.edu.itslibres.android.contactosapp.threads.GuardarContactoThread;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarContactoActivity extends Activity {

	 AccesoContactosWS accesoContactosWS;
	 Contacto contacto;
	 
	 EditText NombreTxt;
	 EditText ApellidosTxt;
	 EditText EmailTxt;
	 EditText TelefonoTxt;
	 
	ImageButton CancelarBtn;
	ImageButton GuardarBtn;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacto_form);
		
		//inicializar los elementos de la vista
		this.NombreTxt = (EditText)findViewById(R.id.NombreTxt);
		this.ApellidosTxt=(EditText)findViewById(R.id.ApellidosTxt);
		this.EmailTxt = (EditText)findViewById(R.id.EmailTxt);
		this.TelefonoTxt = (EditText)findViewById(R.id.TelefonoTxt);
		
        this.CancelarBtn= (ImageButton) findViewById(R.id.CancelarBtn);
        this.GuardarBtn = (ImageButton) findViewById(R.id.GuardarBtn);
        
      //Recuperar los datos recibidos del activity que invoco
        //si se tienen datos entonces procesarlos
        if(getIntent().getExtras() != null)
		{
        	try{
        	//accesoContactosWS = new AccesoContactosWS();
        	
        	Bundle extras = getIntent().getExtras();
	        int idcontacto = extras.getInt("Id");
	        
	        //contacto = accesoContactosWS.DatosContacto(idcontacto);
	        contacto = new CargarContactoThread().execute(idcontacto).get();
	        if(contacto!=null)
	        {
	        //Cuando se tenga conexion al webservice, se recuperará el objeto y se
			//mostrarán en la vista
	        
	        //por ahora mostramos solo el id
	        this.NombreTxt.setText(contacto.getNombre());
	        this.ApellidosTxt.setText(contacto.getApellidos());
	        this.EmailTxt.setText(contacto.getEmail());
	        this.TelefonoTxt.setText(contacto.getTelefono());
	        
	        }else
	        {
	        	Toast.makeText(getBaseContext(), "No se pudo obtener los datos del contacto", Toast.LENGTH_SHORT).show();
	        	finish();
	        }
        	}catch(Exception e)
        	{
        		Toast.makeText(getBaseContext(), "No se pudo obtener los datos del contacto", Toast.LENGTH_SHORT).show();
        		finish();
        	}
		}
        
        
        //al presionar el boton cancelar
        this.CancelarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//regresar a la anteior sin hacer nada
				finish();
				
			}
		});
        
        //al presionar el boton Guardar
        this.GuardarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				//emitir un mensaje
				//Toast.makeText(getBaseContext(), "Los datos del contacto se guardarán", Toast.LENGTH_SHORT).show();
				
				//antes de mandar a guardar, recuperar los nuevos datos de los Txt
				contacto.setNombre(NombreTxt.getText().toString());
				contacto.setApellidos(ApellidosTxt.getText().toString());
				contacto.setEmail(EmailTxt.getText().toString());
				contacto.setTelefono(TelefonoTxt.getText().toString());
				
				try{
					//if(accesoContactosWS.ActualizarContacto(contacto))
					if(new GuardarContactoThread().execute(contacto).get())
					{
						Toast.makeText(getBaseContext(), "Los datos del contacto han sido guardados", Toast.LENGTH_SHORT).show();
						
						//armamos la respuesta de nuestro intent
						//dando una respuesta positiva
						Intent intent = new Intent();
						setResult(RESULT_OK, intent);
				        finish();
					}
					else
						Toast.makeText(getBaseContext(), "Ocurrió un error al actualizar los datos del contacto", Toast.LENGTH_SHORT).show();

					finish();
				
				}catch(Exception e){
				finish();
				}
			}
		});
        
	}

}
