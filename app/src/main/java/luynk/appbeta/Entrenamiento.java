package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.Configuracion;


public class Entrenamiento extends Activity {

    Activity_Entrenamiento_Layout entrenamiento_layout;
    Configuracion config;
    String idUsuario, rootPathUser;
    int entrenamientos;
    int contador_entrenamientos;
    int contador_ruta;
    int[] chosen_ruta;
    int contador_trials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        entrenamientos = Integer.parseInt(config.getIntentos());
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);
        contador_ruta = getIntent().getIntExtra("contador_ruta", 0);
        chosen_ruta = getIntent().getIntArrayExtra("chosen_ruta");
        contador_trials = getIntent().getIntExtra("contador_trials", 0);
        rootPathUser = getIntent().getStringExtra("rootPathUser");

        /*if (contador_entrenamientos>0){

        }*/

        try {
            entrenamiento_layout = new Activity_Entrenamiento_Layout(this);
            setContentView(entrenamiento_layout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Fin Oncreate
    }

    public String getIdUsuario()
    {
        return this.idUsuario;
    }

    public String getRootPathUser()
    {
        return this.rootPathUser;
    }

    public Configuracion getConfig()
    {
        return this.config;
    }
    
    public int getContador_ruta(){return this.contador_ruta;}

    public int getEntrenamientos() { return this.entrenamientos; }

    public int getContador_entrenamientos() { return this.contador_entrenamientos; }

    public int[] getChosen_ruta() {
        return chosen_ruta;
    }

    public int getContador_trials() {
        return contador_trials;
    }

    @Override
    public void onBackPressed() {

    }

}