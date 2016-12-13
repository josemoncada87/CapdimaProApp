package co.edu.icesi.gleo.capdimaproapp.spaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.views.CreateSpacesView;

import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_BANO;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_COCINA;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_COMEDOR;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_CORREDOR;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_HABITACION;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_PATIO;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_SALA;
import static co.edu.icesi.gleo.capdimaproapp.spaces.Space.ESPACIO_SALA_COMEDOR;

/**
 * Created by 1130613425 on 07/12/2016.
 */

public class SpaceView {

    //
    private float x;
    private float y;
    private float anchoEspacio;
    private float altoEspacio;
    private float escala;
    private int tipo;
    //
    private Bitmap habitacionScaled;
    private Bitmap salaComedorScaled;
    private Bitmap banoScaled;
    private Bitmap cocinaScaled;
    private Bitmap salaScaled;
    private Bitmap comedorScaled;
    private Paint borde;
    private Paint pared;
    private Paint piso;
    private Paint pisoPatio;
    private Paint imagen;
    //
    private Space model;

    public SpaceView(Context context, Space model){

        setModelConfig(model);
        setUpColorAndStyle(context);

        Bitmap habitacion = BitmapFactory.decodeResource(context.getResources(), R.drawable.habitacion);
        Bitmap salaComedor = BitmapFactory.decodeResource(context.getResources(),R.drawable.sala_comedor);
        Bitmap bano = BitmapFactory.decodeResource(context.getResources(),R.drawable.bano);
        Bitmap cocina = BitmapFactory.decodeResource(context.getResources(),R.drawable.cocina);
        Bitmap sala = BitmapFactory.decodeResource(context.getResources(),R.drawable.sala);
        Bitmap comedor = BitmapFactory.decodeResource(context.getResources(),R.drawable.comedor);
        habitacionScaled = scaleBitmap(habitacion, escala*2.0f , true);
        salaComedorScaled = scaleBitmap(salaComedor, escala*5.0f , true);
        banoScaled = scaleBitmap(bano, escala*1.5f , true);
        cocinaScaled = scaleBitmap(cocina, escala*2.5f , true);
        salaScaled = scaleBitmap(sala, escala*2.5f , true);
        comedorScaled = scaleBitmap(comedor, escala*1.5f , true);
        // ----------------------------------------------------------------------------------------
        habitacion.recycle();
        salaComedor.recycle();
        bano.recycle();
        cocina.recycle();
        sala.recycle();
        comedor.recycle();
    }

    private void setModelConfig(Space model){
        this.x = model.getX();
        this.y = model.getY();
        System.out.println("valor x: " + this.x+" valor y: " + this.y);
        this.anchoEspacio = model.getAnchoEspacio();
        this.altoEspacio = model.getAltoEspacio();
        this.tipo = model.getTipo();
        this.escala = model.getEscala();
    }

    private void setUpColorAndStyle(Context context) {
        borde = new Paint();
        borde.setStyle(Paint.Style.STROKE);
        borde.setColor(Color.argb(220,10,10,10));
        pared = new Paint();
        pared.setStyle(Paint.Style.FILL);
        pared.setColor(Color.argb(220,120,90,10));
        piso = new Paint();
        piso.setStyle(Paint.Style.FILL);
        piso.setColor(Color.rgb(230,222,198));
        pisoPatio = new Paint();
        pisoPatio.setStyle(Paint.Style.FILL);
        pisoPatio.setColor(context.getResources().getColor(R.color.patioColor));
        imagen = new Paint();
        imagen.setStyle(Paint.Style.FILL);
        imagen.setColor(Color.argb(255,0,0,0));
    }

    public static Bitmap scaleBitmap(Bitmap realImage, float maxImageSize,
                                     boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }


    public void mostrar(Canvas c, float modulo, int modo){
     //   c.drawRect(x-5,y-5,x+ anchoEspacio +6,y+altoEspacio+6, pared);
        c.drawRect(x,y,x+ anchoEspacio,y+altoEspacio, piso);
        mostrarMobilirario(c, modulo);
        mostrarIconosEdicion(c, modo);
    }

    private void mostrarIconosEdicion(Canvas c, int modo) {
        if( modo == CreateSpacesView.MOVE_MODE){
            c.drawRect(x-10,y-10,x+ anchoEspacio +11,y+altoEspacio+11, borde);
        }else if(modo == CreateSpacesView.SCALE_MODE){
            c.drawCircle(x+ anchoEspacio/2, y+altoEspacio/2,(anchoEspacio+altoEspacio)/2, borde);
        }
    }

    private void mostrarMobilirario(Canvas c, float modulo) {
        switch (tipo){
            case ESPACIO_HABITACION:
                c.drawBitmap(habitacionScaled,x,y,imagen);
                break;
            case ESPACIO_BANO:
                c.drawBitmap(banoScaled,x,y,imagen);
                break;
            case ESPACIO_COCINA:
                //c.translate(x,y);
                //c.rotate(45);
                c.drawBitmap(cocinaScaled,x,y,imagen);
                //c.rotate(-45);
                //c.translate(0,0);
                break;
            case ESPACIO_COMEDOR:
                c.drawBitmap(comedorScaled,x,y,imagen);
                break;
            case ESPACIO_CORREDOR:
                // libre de mobiliario
                break;
            case ESPACIO_PATIO:
                c.drawRect(x,y,x+ anchoEspacio,y+altoEspacio, pisoPatio);
                break;
            case ESPACIO_SALA:
                c.drawBitmap(salaScaled,x,y,imagen);
                break;
            case ESPACIO_SALA_COMEDOR:
                c.drawBitmap(salaComedorScaled,x,y,imagen);
                break;

        }
    }

    public boolean validar(float tx, float ty){
        if(tx>x && tx<x+anchoEspacio && ty>y && ty<y+altoEspacio ){
            return true;
        }
        return false;
    }

    public void mover(float tx, float ty){
        x = tx;
        y = ty;
    }

    public void setAltoEspacio(float altoEspacio) {
        this.altoEspacio = altoEspacio;
    }

    public void setAnchoEspacio(float anchoEspacio) {
        this.anchoEspacio = anchoEspacio;
    }


}
