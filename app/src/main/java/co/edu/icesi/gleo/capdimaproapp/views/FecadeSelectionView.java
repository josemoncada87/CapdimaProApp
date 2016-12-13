package co.edu.icesi.gleo.capdimaproapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.activities.CreateSpacesActivity;
import co.edu.icesi.gleo.capdimaproapp.spaces.Space;
import co.edu.icesi.gleo.capdimaproapp.spaces.SpaceView;

/**
 * Created by 1130613425 on 29/06/2016.
 */
public class FecadeSelectionView extends View {

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    private ArrayList<Space> spaces;
    private float modulo25cms;
    private Paint piso;

    private Paint neighborColor;
    private Paint freeOfNeighborColor;
    private Paint[] coloresBorde;
    private boolean[] vecinos;


    public FecadeSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        piso = new Paint();
        piso.setStyle(Paint.Style.FILL);
        piso.setColor(Color.rgb(230,222,198));

        vecinos =  new boolean[4];


        neighborColor = new Paint();
        neighborColor.setColor(Color.GRAY);

        freeOfNeighborColor = new Paint();
        freeOfNeighborColor.setColor(getResources().getColor(R.color.colorAccent));

        coloresBorde = new Paint[4];
        for (int i = 0 ; i < coloresBorde.length ; i++){
            coloresBorde[i] = new Paint();
            coloresBorde[i].setColor(Color.GRAY);
            coloresBorde[i].setAlpha(150);
        }
    }

    public void setModulo25cms(float modulo25cms) {
        this.modulo25cms = modulo25cms;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPaint(piso);
        pintarBorde(canvas);
        for(Space s : spaces){
            System.out.println(s.getX()+"/////"+s.getY());
            s.mostrar(canvas,modulo25cms, CreateSpacesView.FREE_MODE);
        }

        super.onDraw(canvas);
    }

    public void setSpaces(ArrayList<Space> spaces) {
        this.spaces = spaces;
        for(Space s : spaces){
            s.setView(new SpaceView(getContext(),s));
        }
    }

    private void pintarBorde(Canvas canvas){
        canvas.drawRect(0,0, (float) (canvas.getWidth()*0.05),canvas.getHeight(), coloresBorde[LEFT]);
        canvas.drawRect((float) (canvas.getWidth()*0.95), 0, canvas.getWidth() , canvas.getHeight(), coloresBorde[RIGHT]);
        canvas.drawRect(0,0,canvas.getWidth(), (float) (canvas.getHeight()*0.05), coloresBorde[UP]);
        canvas.drawRect(0,(float) (canvas.getHeight()*0.95), canvas.getWidth(), canvas.getHeight(), coloresBorde[DOWN]);
    }

    public void setVecinos(boolean[] vecinos) {
        this.vecinos = vecinos;
        actualizarColorBordes();
    }

    private void actualizarColorBordes() {
        for (int i = 0 ; i < vecinos.length ; i++){
            if(vecinos[i]) {
                coloresBorde[i] = neighborColor;
            }else{
                coloresBorde[i] = freeOfNeighborColor;
            }
        }
    }
}
