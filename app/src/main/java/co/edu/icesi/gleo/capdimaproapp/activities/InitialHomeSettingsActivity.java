package co.edu.icesi.gleo.capdimaproapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.edu.icesi.gleo.capdimaproapp.R;

public class InitialHomeSettingsActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText frente;
    private EditText fondo;
    private EditText pisos;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_home_settings);
        activity = this;
        setUpForm();
        setUpButtons();

        // TODO: Remove on Release
        /*Intent i = new Intent(this, NeighborsDefinitionActivity.class);
        i.putExtra("nombre", "Casa de prueba");
        i.putExtra("frente", ""+5);
        i.putExtra("fondo", ""+10);
        i.putExtra("pisos", ""+1);
        startActivity(i);*/

    }

    private void setUpForm() {
        nombre = (EditText) findViewById(R.id.edt_init_home_set_home_name);
        frente = (EditText) findViewById(R.id.edt_init_home_set_home_front);
        fondo = (EditText) findViewById(R.id.edt_init_home_set_home_depth);
        pisos = (EditText) findViewById(R.id.edt_init_home_set_home_nlevels);
    }

    private void setUpButtons() {
        Button btnConfirm =  (Button) findViewById(R.id.btn_init_home_set_confirm);
        if (btnConfirm != null) {
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String homeName = nombre.getText().toString();
                            String homeFront = frente.getText().toString();
                            String homeDepth = fondo.getText().toString();
                            String homeLevels = pisos.getText().toString();
                            boolean complete = false;
                            if(homeName!=null && homeFront!=null && homeDepth!=null && homeLevels!=null) {
                                if (!homeName.equals("") && !homeFront.equals("") && !homeDepth.equals("")  && !homeLevels.equals("") ) {
                                    Intent i = new Intent(getApplicationContext(), NeighborsDefinitionActivity.class);
                                    i.putExtra("nombre", homeName);
                                    i.putExtra("frente", homeFront);
                                    i.putExtra("fondo", homeDepth);
                                    i.putExtra("pisos", homeLevels);
                                    complete =  true;
                                    startActivity(i);
                                }
                            }
                            if(!complete){
                                Snackbar msn =  Snackbar.make(v, "Información Incompleta", Snackbar.LENGTH_SHORT);
                                msn.setActionTextColor(getResources().getColor(R.color.colorAccent));
                                msn.show();
                            }
                        }
                    });
        }

        Button btnDefineOrientation =  (Button) findViewById(R.id.btn_init_home_set_home_orient);
        if (btnDefineOrientation != null) {
            btnDefineOrientation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar msn =  Snackbar.make(v, "Orientación definida", Snackbar.LENGTH_SHORT);
                    msn.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    msn.show();
                    //
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("La orientación determina la posición del sol, para lograr la adecuada ubiquese frente al lote (por donde se entraría a la vivienda) y presione el botón como si estuviera tomando una foto.").setTitle("Advertencia");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.basic_menu_plus_save_about:
                Intent i = new Intent(getApplicationContext(), AboutCapdima.class);
                startActivity(i);
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
