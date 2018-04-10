package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.Configuracion;


public class RutaPrincipal extends Activity {

    Activity_RutaPrincipal_Layout rutaUno_layoutView;
    Configuracion config;
    String idUsuario, rootPathUser;



    //private ArrayList<Puntos> points = new ArrayList<>();
    int contador_ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        //points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        rootPathUser = getIntent().getStringExtra("rootPathUser");
        contador_ruta = getIntent().getIntExtra("contador_ruta", 0);

        try {
            rutaUno_layoutView = new Activity_RutaPrincipal_Layout(this);
            setContentView(rutaUno_layoutView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Fin Oncreate
    }

    public String getIdUsuario()
    {
        return this.idUsuario;
    }

    public String getRootPathUser() { return this.rootPathUser; }

    public Configuracion getConfig()
    {
        return this.config;
    }

    //public ArrayList<Puntos> getPoints() { return this.points; }

    public int getContador_ruta() {
        return contador_ruta;
    }

    @Override
    public void onBackPressed() {

    }
}