package co.edu.icesi.gleo.capdimaproapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import co.edu.icesi.gleo.capdimaproapp.R;

/**
 * Created by 1130613425 on 29/06/2016.
 */
public class NeighborsSelectionView extends View {


    private static final int IZQUIERDA = 0;
    private static final int ARRIBA = 1;
    private static final int DERECHA = 2;
    private static final int ABAJO = 3;

    private Paint backgroundColor;
    private Paint colorCasa;
    private float ancho;
    private float alto;
    private float modAncho;
    private float modAlto;
    private boolean[] hayVecino;
    private Paint[] colores;



    public NeighborsSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        hayVecino = new boolean[4];
        colores = new Paint[4];

        backgroundColor = new Paint();
        backgroundColor.setColor(Color.WHITE);

        colorCasa = new Paint();
        colorCasa.setColor(getResources().getColor(R.color.colorPrimary));
        for(int i = 0 ; i < hayVecino.length;i++) {
            colores[i] = new Paint();
            colores[i].setColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(backgroundColor);

        ancho = canvas.getWidth();
        alto = canvas.getHeight();
        modAncho = ancho/3;
        modAlto = alto/3;

        // centro - casa
        canvas.drawRect(modAncho,modAlto, 2*modAncho, 2*modAlto, colorCasa);
        //izquierda
        canvas.drawRect(0, modAlto, modAncho, 2*modAlto, colores[IZQUIERDA]);
        //derecha
        canvas.drawRect(2*modAncho, modAlto, ancho, 2*modAlto,colores[DERECHA]);
        //arriba
        canvas.drawRect(modAncho, 0, 2*modAncho, modAlto, colores[ARRIBA]);
        //abajo
        canvas.drawRect(modAncho, 2*modAlto, 2*modAncho, alto, colores[ABAJO]);

        super.onDraw(canvas);
    }

    public boolean[] getHayVecino() {
        return hayVecino;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mx = event.getX();
        float my = event.getY();

        boolean huboCambio = false;

        if(mx>0&& mx<ancho/3 && my > alto/3 && my < (alto/3)+(alto/3)){
            hayVecino[IZQUIERDA] = !hayVecino[IZQUIERDA];
            huboCambio =  true;
        }

        if(mx>ancho/3&& mx<(ancho/3)+(ancho/3) && my > 0 && my < alto/3){
            hayVecino[ARRIBA] = !hayVecino[ARRIBA];
            huboCambio =  true;
        }

        if(mx>(ancho/3)+(ancho/3)&& mx<ancho && my > alto/3 && my < (alto/3)+(alto/3)){
            hayVecino[DERECHA] = !hayVecino[DERECHA];
            huboCambio =  true;
        }

        if(mx>ancho/3&& mx<(ancho/3)+(ancho/3) && my > (alto/3)+(alto/3) && my < alto){
           hayVecino[ABAJO] = !hayVecino[ABAJO];
           huboCambio =  true;
        }

        if(huboCambio) {
            for(int i = 0 ; i < hayVecino.length;i++) {
                if (hayVecino[i]) {
                    colores[i].setColor(Color.GRAY);
                }else{
                    colores[i].setColor(getResources().getColor(R.color.colorAccent));
                }
            }
            invalidate();
            requestLayout();
        }
        return super.onTouchEvent(event);
    }
}
