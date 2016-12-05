package co.edu.icesi.gleo.capdimaproapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // TODO : Remove on release
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);


        setUpButtons();
    }

    private void setUpButtons() {
        Button signin =  (Button) findViewById(R.id.btn_login_signin);
        TextView signup =  (TextView) findViewById(R.id.tv_login_signup);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(i);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
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
