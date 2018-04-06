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
    private ArrayList<Puntos> points = new ArrayList<>();
    int entrenamientos, contador_entrenamientos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        entrenamientos = getIntent().getIntExtra("entrenamiento", 0);
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);

        if (contador_entrenamientos>0){
            rootPathUser = getIntent().getStringExtra("rootPathUser");
        }

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

    public ArrayList<Puntos> getPoints() { return this.points; }

    public int getEntrenamientos() { return this.entrenamientos; }

    public int getContador_entrenamientos() { return this.contador_entrenamientos; }

    @Override
    public void onBackPressed() {

    }

}