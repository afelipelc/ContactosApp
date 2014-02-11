package mx.edu.itslibres.android.contactosapp.activities;

import mx.edu.itslibres.android.contactosapp.R;
import mx.edu.itslibres.android.contactosapp.dataaccess.AccesoContactosWS;
import mx.edu.itslibres.android.contactosapp.models.Contacto;
import mx.edu.itslibres.android.contactosapp.threads.CargarContactoThread;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetallesContactoActivity extends Activity {
	
	//inicializar los elementos de la vista
	TextView NombreLbl;
	TextView ApellidosLbl;
	TextView EmailLbl;
	TextView TelefonoLbl;
	ImageButton EditarBtn;
	ImageButton LlamarBtn;
	ImageButton EnviarEmailBtn;
	ImageButton AgregarContactoBtn;
	 
	 AccesoContactosWS accesoContactosWS;
	 Contacto contacto;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //asignamos el layout contacto_form
        setContentView(R.layout.detalles_contacto);
        
        this.AgregarContactoBtn = (ImageButton) findViewById(R.id.AgregarCImgb);
        this.NombreLbl = (TextView) findViewById(R.id.NombreLbl);
        this.ApellidosLbl = (TextView) findViewById(R.id.ApellidosLbl);
        this.EmailLbl = (TextView)findViewById(R.id.EmailLbl);
        this.TelefonoLbl = (TextView)findViewById(R.id.TelefonoLbl);
        
        this.EditarBtn = (ImageButton)findViewById(R.id.EditarBtn);
        this.LlamarBtn = (ImageButton) findViewById(R.id.LlamarBtn);
        this.EnviarEmailBtn = (ImageButton)findViewById(R.id.EmailBtn);
      //Recuperar los datos recibidos del activity que invoco
        //si se tienen datos entonces procesarlos
        if(getIntent().getExtras() != null)
		{
        	accesoContactosWS = new AccesoContactosWS();
        	
        	Bundle extras = getIntent().getExtras();
	        int idcontacto = extras.getInt("Id");
	        
	        //llamos al metodo que carga los datos de un contacto
	        CargarDatosContacto(idcontacto);
	       
		}
        
        //implementamos el evento clic para AgregarContactoBtn
        this.AgregarContactoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// hacer que abra el nuevo layout
				Intent agregarcontacto = new Intent(DetallesContactoActivity.this, AgregarContactoActivity.class);
				startActivity(agregarcontacto);
			}
		});
        
        this.EditarBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				//creamos el objeto que ayudara a llevar los datos
				Bundle datosextras = new Bundle();
				datosextras.putInt("Id", Integer.valueOf(contacto.getId()));
				
				Intent editarcontacto = new Intent(DetallesContactoActivity.this, EditarContactoActivity.class);
				editarcontacto.putExtras(datosextras);
				//startActivity(editarcontacto);

				//cambiamos la llamada a espera de respuesta
				startActivityForResult(editarcontacto, 0);
				
			}
		});
	
      //Dialogo Yes NO para preguntar si marca al telefono o no
        final AlertDialog.Builder confirmarLlamada = new AlertDialog.Builder(this);
        confirmarLlamada.setMessage("¿Confirma llamar a "+contacto.getNombre() + " "+ contacto.getApellidos() + " ?")
        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Si dijo que si, entonces llamar

            	//aqui lanzar el intent para llamada telefonica
				Intent LanzarLlamada = new Intent(Intent.ACTION_CALL);
				LanzarLlamada.setData(Uri.parse("tel:"+contacto.getTelefono()));
			    startActivity(LanzarLlamada);
            }
        })
        .setNegativeButton("Calcelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            //no hacer nada
            }
        });
        
        this.LlamarBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//aqui lanzar el intent para llamada telefonica
				confirmarLlamada.show();
			}
		});
        
        this.EnviarEmailBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Uri uri = Uri.parse("mailto:"+contacto.getEmail());
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(Intent.createChooser(emailIntent, "Email para "+contacto.getEmail()));
			}
		});
	}

	private void CargarDatosContacto(int idContacto)
	{
		try{
		 //contacto = accesoContactosWS.DatosContacto(idContacto);
		contacto = new CargarContactoThread().execute(idContacto).get();
	        if(contacto!=null)
	        {
	        //Cuando se tenga conexion al webservice, se recuperará el objeto y se
			//mostrarán en la vista
	        
	        //por ahora mostramos solo el id
	        this.NombreLbl.setText(contacto.getNombre());
	        this.ApellidosLbl.setText(contacto.getApellidos());
	        this.EmailLbl.setText(contacto.getEmail());
	        this.TelefonoLbl.setText(contacto.getTelefono());
	        
	        }else
	        	Toast.makeText(getBaseContext(), "No se pudo obtener los datos del contacto", Toast.LENGTH_SHORT).show();
		}catch(Exception e)
		{
			Toast.makeText(getBaseContext(), "No se pudo obtener los datos del contacto", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	//metodo para controlar la respuesta del intent Editar
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 0){
	         if (resultCode == RESULT_OK) {
	        	 
	        	 //se recibio respuesta afirmativa, los datos del contacto fueron actualizados
	        	 //entonces procedemos a recargar los datos
	        	 
	        	//llamos al metodo que carga los datos del contacto
	 	        CargarDatosContacto(contacto.getId());
	         }
	         //si no se actualizaron los datos no se hace nada
		}
	}	
}
