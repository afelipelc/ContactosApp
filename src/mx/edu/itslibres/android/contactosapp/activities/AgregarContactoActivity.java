package mx.edu.itslibres.android.contactosapp.activities;

import mx.edu.itslibres.android.contactosapp.R;
import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import mx.edu.itslibres.android.contactosapp.threads.RegistrarContactoThread;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//agregamos el constructor
public class AgregarContactoActivity extends Activity {
	
	ImageButton CancelarBtn;
	ImageButton GuardarBtn;
	
	 EditText NombreTxt;
	 EditText ApellidosTxt;
	 EditText EmailTxt;
	 EditText TelefonoTxt;
	 
	 AccesoContactosWS accesoContactosWS;
	 Contacto contacto;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //asignamos el layout contacto_form
        setContentView(R.layout.contacto_form);
        
        this.CancelarBtn= (ImageButton) findViewById(R.id.CancelarBtn);
        this.GuardarBtn = (ImageButton) findViewById(R.id.GuardarBtn);
        
		this.NombreTxt = (EditText)findViewById(R.id.NombreTxt);
		this.ApellidosTxt=(EditText)findViewById(R.id.ApellidosTxt);
		this.EmailTxt = (EditText)findViewById(R.id.EmailTxt);
		this.TelefonoTxt = (EditText)findViewById(R.id.TelefonoTxt);
		
        this.CancelarBtn= (ImageButton) findViewById(R.id.CancelarBtn);
        this.GuardarBtn = (ImageButton) findViewById(R.id.GuardarBtn);
        
        
        //al presionar el boton cancelar
        this.CancelarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//finalizar el intent
				finish();
				
			}
		});
        
        //al presionar el boton Guardar
        this.GuardarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				//inicializar el objeto contacto
				contacto = new Contacto();
				
				//antes de mandar a guardar, recuperar los nuevos datos de los Txt
				contacto.setNombre(NombreTxt.getText().toString());
				contacto.setApellidos(ApellidosTxt.getText().toString());
				contacto.setEmail(EmailTxt.getText().toString());
				contacto.setTelefono(TelefonoTxt.getText().toString());
				
				try{
					//inicializamos el objeto que conecta al servicio
					
					accesoContactosWS = new AccesoContactosWS();
					//mandamos a llamar al registro de contacto
					//el objeto recibido lo almacenaremos en el mismo objeto enviado
					//esto hará la sobreescritura de contacto

					//contacto = accesoContactosWS.RegistrarContacto(contacto);
					
					contacto = new RegistrarContactoThread().execute(contacto).get();
					
					//si lo recibido es un objeto lleno, mandar a mostrar el perfil
					//sino informar al usuario del error ocurrido
					if(contacto != null)
					{
						Toast.makeText(getBaseContext(), "El contacto ha sido registrado correctamente", Toast.LENGTH_SHORT).show();
						//preparamos la llamada al intent de detalles
						
						Bundle datosextras = new Bundle();
						datosextras.putInt("Id", contacto.getId());
						
						//pasamos a mostrar el activity de los detalles
						Intent detalles = new Intent(AgregarContactoActivity.this, DetallesContactoActivity.class);
						//antes de llamar al intent, agregamos los extras
						detalles.putExtras(datosextras);
						
						//terminamos este intent y lanzamos el de detalles
						finish();
						
						startActivity(detalles);
						
					}
					else
						Toast.makeText(getBaseContext(), "Ocurrió un error al intentar registrar el nuevo contacto.", Toast.LENGTH_SHORT).show();
				
				}catch(Exception e){
					Toast.makeText(getBaseContext(), "Ocurrió un error al intentar registrar el nuevo contacto.", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
        
	}
}
