package co.edu.icesi.gleo.capdimaproapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.views.CreateSpacesView;

public class CreateSpacesActivity extends AppCompatActivity {

    private CreateSpacesView cvCreateSpace;
    private AlertDialog.Builder createSpacesDialogBuilder;
    private CharSequence[] spaceNames;
    private int generalCanvasZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_spaces);
        setUpCanvas();
        setUpMenuSpaces();
        setUpButtons();
        generalCanvasZoom = 0;
    }

    private void setUpMenuSpaces() {
        spaceNames = new CharSequence[] {"Cocina","Baño","Sala","Comedor","Habitación","Corredor","Patio","Sala Comedor"};
        createSpacesDialogBuilder = new AlertDialog.Builder(this);
        createSpacesDialogBuilder.setTitle("Seleccione el tipo de espacio");
    }

    private void setUpCanvas() {
        Intent prev = getIntent();

        boolean[] vecinos = prev.getBooleanArrayExtra("vecinos");
        String nombre = prev.getStringExtra("nombre");
        String frenteString = prev.getStringExtra("frente");
        String fondoString = prev.getStringExtra("fondo");
        String nPisosString = prev.getStringExtra("pisos");

        int nfrente = Integer.parseInt(frenteString.trim());
        int nfondo = Integer.parseInt(fondoString.trim());
        int npisos = Integer.parseInt(nPisosString.trim());

        cvCreateSpace = (CreateSpacesView) findViewById(R.id.custView_createspaces_canvas);
        cvCreateSpace.setVecinos(vecinos);
        cvCreateSpace.setNombreCasa(nombre);
        cvCreateSpace.setNumeroPisos(npisos);
        cvCreateSpace.setUpHomeSettings(nfrente,nfondo); // 5 , 10 standard value
    }

    private void setUpButtons(){
        ImageButton mover = (ImageButton)findViewById(R.id.btn_createspaces_mod_move);
        mover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvCreateSpace.setModo(CreateSpacesView.MOVE_MODE);
            }
        });
        ImageButton escalar = (ImageButton)findViewById(R.id.btn_createspaces_mod_escale);
        escalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvCreateSpace.setModo(CreateSpacesView.SCALE_MODE);
            }
        });
        ImageButton crear = (ImageButton)findViewById(R.id.btn_createspaces_newspace);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSpacesDialogBuilder.setItems(spaceNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int number) {
                        cvCreateSpace.agregarEspacio(number);
                    }
                });
                createSpacesDialogBuilder.show();
            }
        });
        ImageButton confirmar = (ImageButton)findViewById(R.id.btn_creastespaces_finish);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(getApplicationContext(), FecadeSelectionActivity.class);
               startActivity(i);
            }
        });
        ImageButton borrar  = (ImageButton)findViewById(R.id.btn_creastespaces_delete);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvCreateSpace.setModo(cvCreateSpace.DELETE_MODE);
            }
        });
        ImageButton zoomOut  = (ImageButton)findViewById(R.id.btn_createspaces_zoom_out);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generalCanvasZoom =1;
                cvCreateSpace.setEscala(generalCanvasZoom);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.basic_menu_plus_save_about:
                // TODO:
                break;
            case R.id.basic_menu_plus_save_exit:
                finish();
                break;
            case R.id.basic_menu_plus_save_save:
                // TODO :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate( R.menu.basic_menu_plus_save, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
