package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;

import model.Configuracion;


public class RutaUno extends Activity {

    Activity_RutaUno_Layout rutaUno_layoutView;
    Configuracion config;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ruta_uno);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");


        try {
            rutaUno_layoutView = new Activity_RutaUno_Layout(this);
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

    public Configuracion getConfig()
    {
        return this.config;
    }

    @Override
    public void onBackPressed() {

    }
}