package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import java.util.ArrayList;

import model.Configuracion;

public class Contador extends Activity {

    String idUsuario, str_aux, rootPathUser;
    Configuracion config;
    int entrenamientos, contador_entrenamientos, aux=0;
    private ArrayList<Puntos> points = new ArrayList<>();
    ImageView contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        entrenamientos = getIntent().getIntExtra("entrenamiento", 0);
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);
        str_aux = getIntent().getStringExtra("aux");

        if (str_aux.contains("principal")){
            rootPathUser = getIntent().getStringExtra("rootPathUser");
        }

        contador = (ImageView) findViewById(R.id.ivContador);
        contador.setBackgroundResource(R.drawable.tres);

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                aux++;
                if (aux==1){
                    contador.setBackgroundResource(R.drawable.tres);
                }else if (aux==2){
                    contador.setBackgroundResource(R.drawable.dos);
                }
            }

            public void onFinish() {
                contador.setBackgroundResource(R.drawable.uno);
                //comenzar a mover la figura
                if (str_aux.contains("entrenamiento")){
                    Intent intent = new Intent(Contador.this, Entrenamiento.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("points", points);
                    intent.putExtra("entrenamiento", entrenamientos);
                    intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(Contador.this, RutaPrincipal.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", idUsuario);
                    intent.putExtra("points", points);
                    intent.putExtra("rootPathUser", rootPathUser);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
        //Fin OnCreate
    }
}
