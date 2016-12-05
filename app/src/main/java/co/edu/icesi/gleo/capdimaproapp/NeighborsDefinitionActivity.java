package co.edu.icesi.gleo.capdimaproapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import co.edu.icesi.gleo.capdimaproapp.views.NeighborsSelectionView;

public class NeighborsDefinitionActivity extends AppCompatActivity {

    private String nombre;
    private String frente;
    private String fondo;
    private String pisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbors_definition);

        setUpDataReceived();

        // TODO:  Remove on Release
        NeighborsSelectionView cvSelection = (NeighborsSelectionView)findViewById(R.id.custView_neigh_selection_canvas);
        boolean[] neighborsStates = cvSelection.getHayVecino();
        Intent i = new Intent(this, CreateSpacesActivity.class);
        i.putExtra("vecinos",neighborsStates);
        i.putExtra("nombre",nombre);
        i.putExtra("frente",frente);
        i.putExtra("fondo",fondo);
        i.putExtra("pisos",pisos);
        startActivity(i);
        // -----------------------
        setUpButtons();

    }

    private void setUpDataReceived() {
        Intent prev = getIntent();
        nombre = prev.getStringExtra("nombre");
        frente = prev.getStringExtra("frente");
        fondo = prev.getStringExtra("fondo");
        pisos = prev.getStringExtra("pisos");
    }

    private void setUpButtons() {
        Button confirm = (Button) findViewById(R.id.btn_neigh_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeighborsSelectionView cvSelection = (NeighborsSelectionView)findViewById(R.id.custView_neigh_selection_canvas);
                boolean[] neighborsStates = cvSelection.getHayVecino();
                Intent i = new Intent(getApplicationContext(), CreateSpacesActivity.class);
                i.putExtra("vecinos",neighborsStates);
                i.putExtra("nombre",nombre);
                i.putExtra("frente",frente);
                i.putExtra("fondo",fondo);
                i.putExtra("pisos",pisos);
                startActivity(i);
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
