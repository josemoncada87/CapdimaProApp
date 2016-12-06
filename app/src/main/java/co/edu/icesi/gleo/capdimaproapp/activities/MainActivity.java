package co.edu.icesi.gleo.capdimaproapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.edu.icesi.gleo.capdimaproapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //insertar una espera para el logo
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        // Inicia la actividad de login
    }
}
