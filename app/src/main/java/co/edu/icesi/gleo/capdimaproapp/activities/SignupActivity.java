package co.edu.icesi.gleo.capdimaproapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import co.edu.icesi.gleo.capdimaproapp.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.basic_menu_about:
                Intent i = new Intent(getApplicationContext(), AboutCapdima.class);
                startActivity(i);
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
