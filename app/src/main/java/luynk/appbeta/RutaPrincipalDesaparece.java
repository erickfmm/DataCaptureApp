package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.Configuracion;


public class RutaPrincipalDesaparece extends Activity {

    Activity_RutaPrincipalDesaparece_Layout rutaUnoDesaparece_layoutView;
    Configuracion config;
    String porcentaje, idUsuario, rootPathUser;
    private ArrayList<Puntos> points = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        porcentaje = config.getDesaparece();
        idUsuario = getIntent().getStringExtra("idUsuario");
        points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        rootPathUser = getIntent().getStringExtra("rootPathUser");

        try {
            rutaUnoDesaparece_layoutView = new Activity_RutaPrincipalDesaparece_Layout(this);
            setContentView(rutaUnoDesaparece_layoutView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Fin OnCreate
    }

    public String getIdUsuario()
    {
        return this.idUsuario;
    }

    public String getRootPathUser() {
        return rootPathUser;
    }

    public Configuracion getConfig()
    {
        return this.config;
    }

    public ArrayList<Puntos> getPoints() { return this.points; }

    @Override
    public void onBackPressed() {

    }
}
