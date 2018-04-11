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
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Configuracion;


public class Activity_RutaPrincipalDesaparece_Layout extends View {
    private ArrayList<Puntos> ruta = new ArrayList<>();
    private ArrayList<Puntos> points;// = new ArrayList<>();

    Bitmap elemento;
    float elemento_x, elemento_y;
    int elementoHeight, elementoWidth, contador_ruta, contador_trials, totalTrials;
    boolean flag;
    float corX=Float.NaN, corY=Float.NaN;

    private Paint mPaint;
    private Path mPath;

    int pointPos = 0;
    int [] chosen_ruta;

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
            ruta.add(new Puntos(corX, corY));
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



    public Activity_RutaPrincipalDesaparece_Layout(Context context) throws FileNotFoundException {
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

        config = ((RutaPrincipalDesaparece)getContext()).getConfig();
        idUsuario = ((RutaPrincipalDesaparece)getContext()).getIdUsuario();
        rootPathUser = ((RutaPrincipalDesaparece)getContext()).getRootPathUser();
        contador_ruta = ((RutaPrincipalDesaparece)getContext()).getContador_ruta();
        chosen_ruta = ((RutaPrincipalDesaparece)getContext()).getChosen_ruta();
        contador_trials = ((RutaPrincipalDesaparece)getContext()).getContador_trials();
        totalTrials = ((RutaPrincipalDesaparece)getContext()).getTotalTrials();
        points = Ruta.getRuta(chosen_ruta[contador_ruta], Integer.parseInt(config.getSegundosVelocidad()), getScreenWidth(), getScreenHeight());

        System.out.println("Act_Des-contador ruta: "+contador_ruta);

        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        todayString = formatter.format(todayDate);

        File f = new File(rootPathUser + idUsuario + "_coordDisappear_"+contador_trials+"_"+chosen_ruta[contador_ruta]+"_"+todayString+".txt");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
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

                //Guardar ruta de usuario en archivo
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeChars(Puntos.toCSV(ruta));
                    //oos.writeObject(ruta);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //guardar imagen
                this.setDrawingCacheEnabled(true);
                this.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                Bitmap bitmap = this.getDrawingCache();
                File file = new File(rootPathUser+ idUsuario +"_imageDisappear_"+contador_trials+"_"+chosen_ruta[contador_ruta]+"_"+todayString+".png");
                FileOutputStream ostream;
                try {
                    file.createNewFile();
                    ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.flush();
                    ostream.close();
                    //Toast.makeText(this.getContext(), "Imagen Guardada", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Error when saving the image", Toast.LENGTH_SHORT).show();
                }

                // nueva activity
                contador_ruta++;
                if(contador_ruta!=0 && contador_ruta%chosen_ruta.length==0){
                    contador_ruta = 0;
                    contador_trials++;
                    if(contador_trials==totalTrials){
                        Intent newIntent = new Intent(this.getContext(), SeleccionaConfiguracion.class);
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.getContext().startActivity(newIntent);
                    }else{
                        Intent intent = new Intent(this.getContext(), Contador.class);
                        intent.putExtra("config",config);
                        intent.putExtra("idUsuario", idUsuario);
                        intent.putExtra("rootPathUser", rootPathUser);
                        intent.putExtra("contador_ruta", contador_ruta);
                        intent.putExtra("aux", "principal");
                        intent.putExtra("chosen_ruta", chosen_ruta);
                        intent.putExtra("contador_trials", contador_trials);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.getContext().startActivity(intent);
                    }

                }else{
                    Intent intent = new Intent(this.getContext(), Contador.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("contador_ruta", contador_ruta);
                    intent.putExtra("aux", "desaparece");
                    intent.putExtra("chosen_ruta", chosen_ruta);
                    intent.putExtra("contador_trials", contador_trials);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    this.getContext().startActivity(intent);
                }


            } else {

                // movimiento del objeto
                elemento_x = points.get(pointPos).getX();
                elemento_y = points.get(pointPos).getY();

                pointPos++;

                //Para clase desaparece
                int porcentajeInferior = Integer.parseInt(config.getDesapareceInicio());
                int porcentajeSuperior = Integer.parseInt(config.getDesapareceFinal());

                int canvasSize = canvas.getWidth();
                if (elemento_x>=canvasSize/100*porcentajeInferior && elemento_x<canvasSize/100*porcentajeSuperior)
                {
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                            elemento, 1, 1, false);
                    canvas.drawBitmap(resizedBitmap, elemento_x, elemento_y, null);
                }else{
                    //Dibujar el objeto
                    String figura = Ruta.getFigure(chosen_ruta[contador_ruta]);
                    if (figura.contains("square")){
                        elemento = BitmapFactory.decodeResource(getResources(), R.drawable.cuadrado_peque);
                    }
                    else if (figura.contains("circle")){
                        elemento = BitmapFactory.decodeResource(getResources(), R.drawable.circulo_peque);
                    }
                    else if (figura.contains("triangle")){
                        elemento = BitmapFactory.decodeResource(getResources(), R.drawable.triangulo_peque);
                    }
                    else{
                        elemento = BitmapFactory.decodeResource(getResources(), R.drawable.rombo_peque);
                    }
                    canvas.drawBitmap(elemento, elemento_x, elemento_y, null);
                }


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
                corX = event.getX();
                corY = event.getY();
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                corX = event.getX();
                corY = event.getY();
                mPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                corX = Float.NaN;
                corY = Float.NaN;
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
}


