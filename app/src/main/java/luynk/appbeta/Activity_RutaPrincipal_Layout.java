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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Configuracion;


public class Activity_RutaPrincipal_Layout extends View {

    //Arrays para guardar las rutas del usuario y del objeto
    private ArrayList<Puntos> ruta = new ArrayList<>();
    private ArrayList<Puntos> rutaObjeto = new ArrayList<>();
    private ArrayList<Puntos> points = new ArrayList<>();

    Bitmap elemento;
    float elemento_x=0, elemento_y=0;
    int elementoHeight, elementoWidth, contador_ruta, contador_trials;
    float corX=Float.NaN, corY=Float.NaN;
    boolean flag;
    double speed, percent, seconds, t_aux;

    private Paint mPaint;
    private Path mPath;

    int pointPos = 0;
    int [] chosen_ruta;

    Configuracion config;
    String idUsuario, todayString, rootPathUser;

    PrintWriter fos;
    PrintWriter fosObj;

    private final static int interval = 5;
    Handler mHandler = new Handler();

    //Hanbler que corre cada 5 milisegundos en el background
    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
            ruta.add(new Puntos(corX, corY));
            rutaObjeto.add(new Puntos(elemento_x, elemento_y));
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



    public Activity_RutaPrincipal_Layout(Context context) throws FileNotFoundException {
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

        config = ((RutaPrincipal)getContext()).getConfig();
        idUsuario = ((RutaPrincipal)getContext()).getIdUsuario();
        rootPathUser = ((RutaPrincipal)getContext()).getRootPathUser();
        contador_ruta = ((RutaPrincipal)getContext()).getContador_ruta();
        chosen_ruta = ((RutaPrincipal)getContext()).getChosen_ruta();
        contador_trials = ((RutaPrincipal)getContext()).getContador_trials();
        points = Ruta.getRuta(chosen_ruta[contador_ruta], Integer.parseInt(config.getSegundosVelocidad()), getScreenWidth(), getScreenHeight());

        System.out.println("Act_Prin-contador ruta: "+contador_ruta);

        Date todayDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        todayString = formatter.format(todayDate);

        //Crear archivo para ruta de usuario
        File f = new File(rootPathUser + idUsuario + "_coordUser_"+contador_trials+"_"+chosen_ruta[contador_ruta]+"_"+todayString+".txt");
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
            fos = new PrintWriter(f, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "We need Write External Storage permission. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        }

        //Crear archivo para ruta de objeto
        File f2 = new File(rootPathUser + idUsuario + "_coordFigure_"+contador_trials+"_"+chosen_ruta[contador_ruta]+"_"+todayString+".txt");
        if (f2.exists()) {
            f2.delete();
        }
        try {
            f2.createNewFile();
            fosObj = new PrintWriter(f2, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //inicio handler
        startRepeatingTask();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);

        if (flag) {

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

            //Guardar ruta usuario en archivo
            try {
                fos.write(Puntos.toCSV(ruta));
                fos.close();
                /*ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeChars(Puntos.toCSV(ruta));
                //oos.writeObject(ruta);
                oos.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Guardar ruta objeto en archivo
            try {
                fosObj.write(Puntos.toCSV(rutaObjeto));
                fosObj.close();
                /*ObjectOutputStream oos = new ObjectOutputStream(fosObj);
                oos.writeChars(Puntos.toCSV(rutaObjeto));
                //oos.writeObject(rutaObjeto);
                oos.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            //guardar imagen
            this.setDrawingCacheEnabled(true);
            this.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            Bitmap bitmap = this.getDrawingCache();
            File file = new File(rootPathUser + idUsuario + "_image_" +contador_trials+"_"+chosen_ruta[contador_ruta]+"_"+ todayString + ".png");
            FileOutputStream ostream;

            try {
                file.createNewFile();
                ostream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                ostream.flush();
                ostream.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this.getContext(), "Error when saving the image", Toast.LENGTH_SHORT).show();
            }

            // nueva activity
            contador_ruta++;
            if (contador_ruta != 0 && contador_ruta % chosen_ruta.length == 0) {
                contador_ruta = 0;
                Intent intent = new Intent(this.getContext(), Contador.class);
                intent.putExtra("config", config);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("rootPathUser", rootPathUser);
                //newIntent.putExtra("points", points);
                intent.putExtra("contador_ruta", contador_ruta);
                intent.putExtra("chosen_ruta", chosen_ruta);
                intent.putExtra("contador_trials", contador_trials);
                intent.putExtra("aux", "desaparece");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.getContext().startActivity(intent);
            } else {
                Intent intent = new Intent(this.getContext(), Contador.class);
                intent.putExtra("config", config);
                intent.putExtra("idUsuario", idUsuario);
                intent.putExtra("rootPathUser", rootPathUser);
                //newIntent.putExtra("points", points);
                intent.putExtra("contador_ruta", contador_ruta);
                intent.putExtra("chosen_ruta", chosen_ruta);
                intent.putExtra("contador_trials", contador_trials);
                intent.putExtra("aux", "principal");
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


