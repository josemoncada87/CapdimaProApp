package co.edu.icesi.gleo.capdimaproapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import co.edu.icesi.gleo.capdimaproapp.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setUpButtons();

        /*// TODO: Remove on Release
        Intent i = new Intent(this, InitialHomeSettingsActivity.class);
        startActivity(i);*/

    }

    private void setUpButtons() {
        CardView newHouse =  (CardView) findViewById(R.id.cv_mainmenu_new_house);
        CardView openHouse =  (CardView) findViewById(R.id.cv_mainmenu_open_house);
        CardView about =  (CardView) findViewById(R.id.cv_mainmenu_about);

        newHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InitialHomeSettingsActivity.class);
                startActivity(i);
            }
        });

        openHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OpenHouseActivity.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TODO: Create About Activity
               // Intent i = new Intent(getApplicationContext(), AboutCapdima.class);//
               // startActivity(i);
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.basic_menu_about:
                // TODO:
            break;
            case R.id.basic_menu_exit:
                finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate( R.menu.basic_menu_about_exit, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
