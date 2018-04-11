package luynk.appbeta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Configuracion;


public class Activity_Entrenamiento_Layout extends View {

    //Arrays para guardar las rutas del usuario
    private ArrayList<Posicion> ruta = new ArrayList<>();
    private ArrayList<Posicion> rutaObjeto = new ArrayList<>();
    private ArrayList<Puntos> points;// = new ArrayList<>();

    Bitmap elemento;
    float elemento_x=0, elemento_y=0;
    int elementoHeight, elementoWidth, contador_trials;
    String corX="NaN", corY="NaN";
    boolean flag;
    double speed, percent, seconds, t_aux;
    int[] chosen_ruta;

    private Paint mPaint;
    private Path mPath;

    int pointPos = 0, entrenamientos, contador_entrenamientos, contador_ruta;

    Configuracion config;
    String idUsuario, todayString, rootPathUser;

    FileOutputStream fos;

    private final static int interval = 5;
    Handler mHandler = new Handler();

    //Hanbler que corre cada 5 milisegundos en el background
    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            ruta.add(new Posicion(corX, corY));
            rutaObjeto.add(new Posicion(String.valueOf(elemento_x), String.valueOf(elemento_y)));
            mHandler.postDelayed(mHandlerTask, interval);
        }
    };

    //inicializar handler
    void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    //detener handler
    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }



    public Activity_Entrenamiento_Layout(Context context) throws FileNotFoundException {
        super(context);

        elemento_x = 0;
        elemento_y = getScreenHeight()/2;
        flag = true;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
        seconds = 5;
        percent = 100/seconds; //Percent of screen in 1 second
        speed = percent/6000;
        t_aux = 1 / (seconds*60);

        config = ((Entrenamiento)getContext()).getConfig();
        idUsuario = ((Entrenamiento)getContext()).getIdUsuario();
        //points = ((Entrenamiento)getContext()).getPoints();
        entrenamientos = ((Entrenamiento)getContext()).getEntrenamientos();
        contador_entrenamientos = ((Entrenamiento)getContext()).getContador_entrenamientos();
        contador_ruta = ((Entrenamiento)getContext()).getContador_ruta();
        chosen_ruta = ((Entrenamiento)getContext()).getChosen_ruta();
        contador_trials = ((Entrenamiento)getContext()).getContador_trials();
        rootPathUser = ((Entrenamiento)getContext()).getRootPathUser();

        System.out.println("Act_Entr-contador entrenamientos: "+contador_entrenamientos);
        System.out.println("Act_Entr-contador ruta: "+contador_ruta);
        points = Ruta.getRuta(chosen_ruta[contador_ruta], Integer.parseInt(config.getSegundosVelocidad()), getScreenWidth(), getScreenHeight());

        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        todayString = formatter.format(todayDate);



        //Crear archivo para rutas de entrenamiento
        File f = new File(rootPathUser + idUsuario+"_coord_training_"+todayString+".txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "We need Write External Storage permission. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
            }
        }
        fos = new FileOutputStream(f);
        //inicio handler
        startRepeatingTask();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);

        //flag para controlar ejecucion de metodo onDraw
        if(flag) {

            //Crear objeto para obtener ancho y largo
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), R.drawable.cuadrado_peque, options);
            elementoHeight = options.outHeight;
            elementoWidth = options.outWidth;

            //finalizar metodo si se llega al final de la pantalla
            if (elemento_x >= canvas.getWidth() - elementoWidth) {
                flag = false;

                stopRepeatingTask();

                //Calcular error en la trayectoria
                float yuser, yobj;
                double suma=0;

                for (int i=0; i<ruta.size(); i++){
                    String aux = ruta.get(i).getY();
                    String aux2 = rutaObjeto.get(i).getY();
                    if (aux.contains("NaN")){
                        yuser = 0;
                    }else {
                        String[] arrayString = aux.split(":");
                        yuser = Float.parseFloat(arrayString[1]);
                    }
                    yobj = Float.parseFloat(aux2.substring(2,7));

                    //Log.d("aux", String.valueOf(yuser));
                    //Log.d("aux", aux2.substring(2,7));
                    //Log.d("aux", String.valueOf(Math.pow((yobj-yuser),2)));
                    suma = suma + Math.pow((yobj-yuser),2);
                }

                suma = suma / ruta.size();
                Log.d("Raiz", String.valueOf(Math.sqrt(suma)));


                //Guardar ruta usuario en archivo
                try {
                    if (contador_entrenamientos == 1){
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(ruta);
                        oos.writeBytes("Fin entrenamiento "+contador_entrenamientos);
                        oos.close();
                    }else{
                        AppendingObjectOutputStream oos = new AppendingObjectOutputStream(fos);
                        oos.writeObject(ruta);
                        oos.writeBytes("Fin entrenamiento "+contador_entrenamientos);
                        oos.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // nueva activity
                contador_ruta++;
                if(contador_ruta!=0 && contador_ruta %chosen_ruta.length == 0){
                    contador_entrenamientos++;
                    contador_ruta = 0;
                }

                if (contador_entrenamientos == entrenamientos){
                    contador_ruta = 0;
                    Intent intent = new Intent(this.getContext(), ExplicacionPrincipal.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("entrenamiento", entrenamientos);
                    intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                    intent.putExtra("contador_ruta", contador_ruta);
                    intent.putExtra("chosen_ruta", chosen_ruta);
                    intent.putExtra("contador_trials", contador_trials);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.getContext().startActivity(intent);
                }else {
                    Intent intent = new Intent(this.getContext(), Contador.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("entrenamiento", entrenamientos);
                    intent.putExtra("aux", "entrenamiento");
                    intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                    intent.putExtra("contador_ruta", contador_ruta);
                    intent.putExtra("chosen_ruta", chosen_ruta);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.getContext().startActivity(intent);
                }

            } else {
                elemento_x = points.get(pointPos).getX();
                elemento_y = points.get(pointPos).getY();

                pointPos++;

                //Dibujar el objeto
                String figura = Ruta.getFigure(chosen_ruta[contador_ruta]);
                if (figura.contains("square"))
                    elemento = BitmapFactory.decodeResource(getResources(), R.drawable.cuadrado_peque);
                else if (figura.contains("circle"))
                    elemento = BitmapFactory.decodeResource(getResources(), R.drawable.circulo_peque);
                else if (figura.contains("triangle"))
                    elemento = BitmapFactory.decodeResource(getResources(), R.drawable.triangulo_peque);
                else
                    elemento = BitmapFactory.decodeResource(getResources(), R.drawable.rombo_peque);

                canvas.drawBitmap(elemento, elemento_x, elemento_y, null);

                invalidate();
            }
        }
    }

    //metodo que se ejecuta en cada touch
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                corX = String.valueOf(Math.round(event.getX()));
                corY = String.valueOf(Math.round(event.getY()));
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                corX = String.valueOf(Math.round(event.getX()));
                corY = String.valueOf(Math.round(event.getY()));
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                corX = "NaN";
                corY = "NaN";
                break;
        }

        return true;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    public class AppendingObjectOutputStream extends ObjectOutputStream {

        AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            // do not write a header, but reset:
            // this line added after another question
            // showed a problem with the original
            reset();
        }

    }
}




