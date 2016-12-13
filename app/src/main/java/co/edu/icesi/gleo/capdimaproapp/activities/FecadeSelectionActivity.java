package co.edu.icesi.gleo.capdimaproapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.spaces.Space;
import co.edu.icesi.gleo.capdimaproapp.spaces.SpaceView;
import co.edu.icesi.gleo.capdimaproapp.views.FecadeSelectionView;

public class FecadeSelectionActivity extends AppCompatActivity {

    private Connection conexionMySQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecade_selection);



        Intent prev = getIntent();
        Bundle b = prev.getExtras();
        ArrayList<Space> espacios = (ArrayList<Space>) b.get("espacios");
        boolean[] vecinos = prev.getBooleanArrayExtra("vecinos");

        for(int i = 0 ; i < espacios.size() ; i++){
            System.out.println("-------------->"+espacios.get(i).getX()+"/"+espacios.get(i).getY());
        }
        float mod25 = b.getFloat("mod25");

        FecadeSelectionView v = (FecadeSelectionView) findViewById(R.id.viewFecadeSelection);
        v.setModulo25cms(mod25);
        v.setSpaces(espacios);
        v.setVecinos(vecinos);


        System.out.println("espacios: " + espacios.size());



        //consultaVanos();
        //consulta();
    }

    public void consulta(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conexionMySQL = DriverManager.getConnection( "jdbc:mysql://172.16.0.105:3306/LMQUINTERO", "LMQUINTERO", "QIDN4GT" );
                    System.out.println(conexionMySQL.toString());

                    String SQLEjecutar = "select * from vanos WHERE fitness>6.5";
                    Statement st = conexionMySQL.createStatement();
                    ResultSet rs = st.executeQuery(SQLEjecutar);
                    String resultadoSQL = "";
                    Integer numColumnas = 0;
                    // número de columnas (campos) de la consula SQL
                    numColumnas = rs.getMetaData().getColumnCount();
                    System.out.println("numero de columnas obtenidas: " + numColumnas);

                    // mostramos el resultado
                    int cont = 0;
                    while (rs.next()) {
                        cont++;
                        //VanoModel vanoTemp = new VanoModel();
                        for (int j = 1; j <= numColumnas; j++) {
                            /*vanoTemp.addValue(rs.getObject(j), rs.getMetaData()
                                    .getColumnName(j));*/
                        }
                        //vanoTemp.init();
                        //vanosRef.add(vanoTemp);
                    }
                    //  System.out.println("tamaño en objetos: " + cont);
                    System.out.println(resultadoSQL);
                    System.out.println("numero: " + cont + "/11000");
                    st.close();
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    public void conectarBDMySQL(String usuario, String contrasena, String ip,
                                String puerto, String catalogo) {
        if (conexionMySQL == null) {
            String urlConexionMySQL = "";

            if (catalogo == "") {
                urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/"
                        + catalogo;
                System.out.println("conexion: " + urlConexionMySQL);
            } else {
                urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto;
            }
            if (usuario != "" & contrasena != "" & ip != "" & puerto != "") {
                try {
                    //Class.forName("com.mysql.jdbc.Driver");

                    conexionMySQL = DriverManager.getConnection( urlConexionMySQL, usuario, contrasena );
                    System.out.println(conexionMySQL.toString());

                /*} catch (ClassNotFoundException e) {
                    System.err.println("ClassNotFound: " + e);
                */} catch (SQLException e) {
                    System.err.println("SQLException: " + e);
                }
            }
        }
    }

    public void consultaVanos() {
        //ArrayList<VanoModel> vanosRef = new ArrayList<VanoModel>();
        try {

            conectarBDMySQL("LMQUINTERO", "QIDN4GT", "172.16.0.105", "3306",
                    "LMQUINTERO");

            String SQLEjecutar = "select * from vanos WHERE fitness>6.8";
            Statement st = conexionMySQL.createStatement();
            ResultSet rs = st.executeQuery(SQLEjecutar);
            String resultadoSQL = "";
            Integer numColumnas = 0;
            // número de columnas (campos) de la consula SQL
            numColumnas = rs.getMetaData().getColumnCount();
            System.out.println("numero de columnas obtenidas: " + numColumnas);

            /*// mostramos el resultado
            int cont = 0;
            while (rs.next()) {
                cont++;
                VanoModel vanoTemp = new VanoModel();
                for (int j = 1; j <= numColumnas; j++) {
                    vanoTemp.addValue(rs.getObject(j), rs.getMetaData()
                            .getColumnName(j));
                }
                vanoTemp.init();
                vanosRef.add(vanoTemp);
            }
            System.out.println("tamaño en objetos: " + vanosRef.size());
            System.out.println(resultadoSQL);
            System.out.println("numero: " + cont + "/11000");*/
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  return (new GrupoBaseVanos(vanosRef));
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
