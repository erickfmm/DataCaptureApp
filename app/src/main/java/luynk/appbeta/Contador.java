package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import java.util.ArrayList;
import model.Configuracion;

public class Contador extends Activity {

    String idUsuario, str_aux, rootPathUser;
    Configuracion config;
    int contador_entrenamientos, contador_ruta;
    //private ArrayList<Puntos> points = new ArrayList<>();
    ImageView contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        //points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        //entrenamientos = getIntent().getIntExtra("entrenamiento", 0);
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);
        contador_ruta = getIntent().getIntExtra("contador_ruta", 0);
        str_aux = getIntent().getStringExtra("aux");

        if (str_aux.contains("principal") || str_aux.contains("desaparece")){
            rootPathUser = getIntent().getStringExtra("rootPathUser");
        }

        contador = (ImageView) findViewById(R.id.ivContador);
        contador.setBackgroundResource(R.drawable.tres);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {

                Log.d("mili", String.valueOf(millisUntilFinished));
                if (millisUntilFinished >= 3000){
                    contador.setBackgroundResource(R.drawable.tres);
                }else if (millisUntilFinished >= 2000){
                    contador.setBackgroundResource(R.drawable.dos);
                }else {
                    contador.setBackgroundResource(R.drawable.uno);
                }
            }

            public void onFinish() {
                contador.setBackgroundResource(R.drawable.uno);
                //comenzar a mover la figura
                if (str_aux.contains("entrenamiento")){
                    Intent intent = new Intent(Contador.this, Entrenamiento.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    //intent.putExtra("points", points);
                    //intent.putExtra("entrenamiento", entrenamientos);
                    intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                    intent.putExtra("contador_ruta", contador_ruta);
                    startActivity(intent);
                    finish();
                }else if(str_aux.contains("desaparece")){
                    Intent intent = new Intent(Contador.this, RutaPrincipalDesaparece.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("contador_ruta", contador_ruta);
                    //newIntent.putExtra("points", points);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent = new Intent(Contador.this, RutaPrincipal.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    //intent.putExtra("points", points);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("contador_ruta", contador_ruta);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
        //Fin OnCreate
    }
}
