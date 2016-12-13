package co.edu.icesi.gleo.capdimaproapp.spaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcelable;

import java.io.Serializable;

import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.views.CreateSpacesView;

/**
 * Created by 1130613425 on 30/06/2016.
 */
public class Space implements Serializable{

    //
    private float x;
    private float y;
    private float anchoEspacio;
    private float altoEspacio;
    private float escala;
    private int tipo;
    //
    public static final int ESPACIO_COCINA = 0;
    public static final int ESPACIO_BANO = 1;
    public static final int ESPACIO_SALA = 2;
    public static final int ESPACIO_COMEDOR = 3;
    public static final int ESPACIO_HABITACION = 4;
    public static final int ESPACIO_CORREDOR = 5;
    public static final int ESPACIO_PATIO = 6;
    public static final int ESPACIO_SALA_COMEDOR = 7;

    private transient SpaceView view;

    public Space(int tipo, Context context, float escala){
        this.x = 100;
        this.y = 200;
        this.anchoEspacio = 215;
        this.altoEspacio = 215;
        this.tipo = tipo;
        this.escala = escala;
    }

    public float getEscala() {
        return escala;
    }

    public int getTipo() {
        return tipo;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAltoEspacio() {
        return altoEspacio;
    }

    public float getAnchoEspacio() {
        return anchoEspacio;
    }

    public void escalarAncho(float tw){
        anchoEspacio = tw;
        view.setAnchoEspacio(tw);
    }

    public void escalarAlto(float th){
        altoEspacio = th;
        view.setAltoEspacio(th);
    }

    public boolean validar(float tx, float ty){
       return view.validar(tx,ty);
    }

    public void mover(float tx, float ty){
        this.x = tx;
        this.y = ty;
        view.mover(tx,ty);
    }

    public void setView(SpaceView view) {
        this.view = view;
    }

    public void mostrar(Canvas c, float modulo, int modo){
       view.mostrar(c,modulo,modo);
    }
}

