package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.Configuracion;


public class RutaPrincipalDesaparece extends Activity {

    Activity_RutaPrincipalDesaparece_Layout rutaUnoDesaparece_layoutView;
    Configuracion config;
    String idUsuario, rootPathUser;
    int contador_ruta;
    int contador_trials;
    int totalTrials;
    int[] chosen_ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        contador_ruta = getIntent().getIntExtra("contador_ruta", 0);
        rootPathUser = getIntent().getStringExtra("rootPathUser");
        chosen_ruta = getIntent().getIntArrayExtra("chosen_ruta");
        contador_trials = getIntent().getIntExtra("contador_trials", 0);
        totalTrials = Integer.parseInt(config.getTrialNumber());

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

    public int getContador_ruta() {
        return contador_ruta;
    }

    public int getContador_trials() {
        return contador_trials;
    }

    public int getTotalTrials() {
        return totalTrials;
    }

    public int[] getChosen_ruta() {
        return chosen_ruta;
    }

    @Override
    public void onBackPressed() {

    }
}
