package co.edu.icesi.gleo.capdimaproapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import co.edu.icesi.gleo.capdimaproapp.R;
import co.edu.icesi.gleo.capdimaproapp.spaces.Space;

/**
 * Created by 1130613425 on 29/06/2016.
 */
public class CreateSpacesView extends View {

    private static final int LEFT = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int DOWN = 3;

    public static final int MOVE_MODE = 0;
    public static final int SCALE_MODE = 1;
    public static final int DELETE_MODE = 2;

    private Paint backgroundColor;
    private Paint neighborColor;
    private Paint freeOfNeighborColor;
    private Paint gridLineColor;
    private Paint workout;

    //  TODO: Make an object that represents houses, maybe useful for db managment
    private String nombreCasa;
    private int numeroPisos;

    private Paint[] coloresBorde;
    private boolean[] vecinos;

    private ArrayList<Space> spaces;
    private ArrayList[] pisos;
    private int pisoActual;
    private Space selector;
    private int modo; // define el modo de trabajo: mover 0 escalar 1
    private float prevPosX;
    private float prevPosY;

    // Configuración de Grid
    private float ancho;
    private float alto;
    private float frente;
    private float fondo;
    private float moduloAncho;
    private float moduloAlto;
    private float moduloMetro;
    private float modulo25cms;

    private boolean anchoAltoReady;
    private boolean frenteFondoReady;
    private boolean gridReady;

    private float escala;

    public CreateSpacesView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setUpColors();

        selector =  null;
        vecinos =  new boolean[4];
        pisoActual = 0;
        modo = MOVE_MODE;
        spaces =  new ArrayList<>();
        setUpFloors();

        // inicializacion base de la Grid
        escala = 1;
        ancho   = 1;
        alto    = 1;
        frente  = 1;
        fondo   = 1;
        moduloAncho = ancho/frente;
        moduloAlto = alto/fondo;
        moduloMetro = moduloAncho > moduloAlto ? moduloAlto : moduloAncho;
        modulo25cms = moduloMetro / 4;

        // control configuracion de la grid
        anchoAltoReady = false;
        frenteFondoReady = false;
        gridReady =  false;
    }

    public void setUpHomeSettings(float dFrente, float dFondo){
        frente = dFrente;
        fondo =  dFondo;
        frenteFondoReady = true;
    }

    private void setUpColors() {
        backgroundColor =  new Paint();
        backgroundColor.setColor(getResources().getColor(R.color.cardview_light_background));

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

        gridLineColor =  new Paint();
        gridLineColor.setStyle(Paint.Style.STROKE);
        gridLineColor.setColor(Color.argb(100,0,0,0));
        gridLineColor.setStrokeWidth(2);

        workout =  new Paint();
        workout.setStyle(Paint.Style.FILL);
        workout.setColor(Color.argb(100,0,255,0));
    }

    public void configuracionGrid(Canvas canvas){
        if(!anchoAltoReady){
            ancho =  canvas.getWidth();
            alto =  canvas.getHeight();
            anchoAltoReady =  true;
        }
        if(anchoAltoReady && frenteFondoReady){
            moduloAncho = (float) (((ancho*0.90) / frente));
            moduloAlto = (float) (((alto*0.90) / fondo));
            moduloMetro = moduloAncho > moduloAlto ? moduloAlto : moduloAncho;
            modulo25cms = moduloMetro / 4;
            gridReady =  true;
        }
        invalidate();
        requestLayout();
    }

    private void setUpFloors() {
        pisos =  new ArrayList[1];
        for (int i = 0 ; i < pisos.length ; i++){
            pisos[i] = new ArrayList<>();
        }
        pisos[0] = spaces; // TODO: Remove on Release
    }

    private void mostrarGrid(Canvas canvas) {
        float inicioAreaDeTrabajoX = ancho* 0.05f;
        float inicioAreaDeTrabajoY = alto* 0.05f;
        long cantidadDeLineasX = Math.round( (ancho-(2*inicioAreaDeTrabajoX)) / modulo25cms);
        long cantidadDeLineasY = Math.round( (alto-(2*inicioAreaDeTrabajoY)) / modulo25cms);
        for (int i = 0; i < cantidadDeLineasX; i++) {
            canvas.drawLine((float)inicioAreaDeTrabajoX, (float)inicioAreaDeTrabajoY, (float)inicioAreaDeTrabajoX, (float)(alto-inicioAreaDeTrabajoY), gridLineColor);
            inicioAreaDeTrabajoX += modulo25cms;
        }
        inicioAreaDeTrabajoX = ancho * 0.05f;
        inicioAreaDeTrabajoY = alto * 0.05f;
        for (int i = 0; i < cantidadDeLineasY; i++) {
            canvas.drawLine(inicioAreaDeTrabajoX, inicioAreaDeTrabajoY, (ancho-inicioAreaDeTrabajoX), inicioAreaDeTrabajoY, gridLineColor);
            inicioAreaDeTrabajoY += modulo25cms;
        }
        if(frente>=fondo) {
            // come abajo
            canvas.drawRect((ancho * 0.05f)-1, ((alto * 0.05f) + (fondo * modulo25cms * 4)), ancho - inicioAreaDeTrabajoX, (alto * 0.95f), coloresBorde[DOWN]);
        }else {
            // come a la derecha
            canvas.drawRect( (frente*modulo25cms*4) + ancho*0.05f , alto*0.05f , ancho*0.95f, (alto * 0.95f), coloresBorde[RIGHT]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.save();
        //canvas.translate(mmx, mmy);
        //canvas.scale(escala,escala);
        if(!gridReady) {
            configuracionGrid(canvas);
        }else {
            canvas.drawPaint(backgroundColor);
            pintarBorde(canvas);
            mostrarGrid(canvas);
            for(int i = 0 ; i < pisos[pisoActual].size() ; i++) {
                ((Space)pisos[pisoActual].get(i)).mostrar(canvas, modulo25cms, modo);
            }
        }
        /* draw whatever you want scaled at 0,0*/
        //canvas.restore();
        super.onDraw(canvas);
    }

   // private Space deleted;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tx = event.getX();
        float ty = event.getY();
        boolean huboCambio = false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (int i = spaces.size()-1 ; i >= 0 ; i--) {
                Space space = spaces.get(i);
                if (space.validar(tx, ty)) {
                    selector = space;
                   // deleted =  selector;
                    huboCambio = true;
                    break;
                }
            }
            if(selector!= null && modo == DELETE_MODE){
                removerEspacio(selector);
                Snackbar msn =  Snackbar.make(this, "ESPACIO ELIMINADO", Snackbar.LENGTH_LONG);
                msn.setActionTextColor(getResources().getColor(R.color.colorAccent));
                /*  msn.setAction("DESHACER", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spaces.add(deleted);
                        System.out.println("CANTIDAD DE ESPACIOS: " + spaces.size());
                    }
                });*/
                msn.show();
                huboCambio =  true;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (selector != null) {
                if(modo == MOVE_MODE) {
                    float snapX = (((int)(tx/modulo25cms))*modulo25cms)+(ancho* 0.05f);
                    float snapY = (((int)(ty/modulo25cms))*modulo25cms)+(alto* 0.05f);
                    selector.mover(snapX, snapY);
                }else if(modo == SCALE_MODE){
                    int historySize = event.getHistorySize();
                    if (historySize > 0) {
                        prevPosX = event.getHistoricalX(historySize - 1);
                        prevPosY = event.getHistoricalY(historySize - 1);
                    }
                    float distW = Math.abs(tx - selector.getX());
                    float distH = Math.abs(ty - selector.getY());
                    float snapX = (((int)(distW/modulo25cms))*modulo25cms);
                    float snapY = (((int)(distH/modulo25cms))*modulo25cms);
                    if (tx != prevPosX) {
                        selector.escalarAncho(snapX);
                    }
                    if (ty != prevPosY) {
                        selector.escalarAlto(snapY);
                    }
                }
                huboCambio = true;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (selector != null) {
                selector = null;
                huboCambio = true;
            }
        }
        if(huboCambio){
            invalidate();
            requestLayout();
        }
        return huboCambio;
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

    public void setModo(int modo) {
        this.modo = modo;
        invalidate();
        requestLayout();
    }

    private void pintarBorde(Canvas canvas){
        canvas.drawRect(0,0, (float) (canvas.getWidth()*0.05),canvas.getHeight(), coloresBorde[LEFT]);
        canvas.drawRect((float) (canvas.getWidth()*0.95), 0, canvas.getWidth() , canvas.getHeight(), coloresBorde[RIGHT]);
        canvas.drawRect(0,0,canvas.getWidth(), (float) (canvas.getHeight()*0.05), coloresBorde[UP]);
        canvas.drawRect(0,(float) (canvas.getHeight()*0.95), canvas.getWidth(), canvas.getHeight(), coloresBorde[DOWN]);
    }

    public void removerEspacio(Space ref){
        spaces.remove(ref);
        invalidate();
        requestLayout();
    }

    public void agregarEspacio(int tipoDeEspacio) {

        //spaces.add(new Space(tipoDeEspacio, getContext(), moduloMetro));
        pisos[pisoActual].add(new Space(tipoDeEspacio, getContext(), moduloMetro));

        invalidate();
        requestLayout();

        /*switch (tipoDeEspacio){
            case ESPACIO_COCINA:
                spaces.add(new Space(ESPACIO_COCINA));
                break;
            case ESPACIO_BANO:
                spaces.add(new Space(ESPACIO_COCINA));
                break;
            case ESPACIO_SALA:
                spaces.add(new Space(ESPACIO_COCINA));
                break;
            case ESPACIO_COMEDOR:
                spaces.add(new Space(ESPACIO_COCINA));
                break;
            case ESPACIO_HABITACION:
                break;
            case ESPACIO_CORREDOR:
                break;
            case ESPACIO_PATIO:
                break;
            case ESPACIO_SALA_COMEDOR:
                break;
        }*/

    }

    public void setEscala(float escala) {
        this.escala = escala;
        invalidate();
        requestLayout();
    }

    public void setNombreCasa(String nombreCasa) {
        this.nombreCasa = nombreCasa;
    }

    public void setNumeroPisos(int numeroPisos) {
        this.numeroPisos = numeroPisos;
    }
}
