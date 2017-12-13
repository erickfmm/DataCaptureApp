package luynk.appbeta;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileNotFoundException;

import model.Configuracion;


public class RutaUnoDesaparece extends Activity {
    Activity_RutaUnoDesaparece_Layout rutaUnoDesaparece_layoutView;

    Configuracion config;
    String porcentaje, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = getIntent().getParcelableExtra("config");
        porcentaje = config.getDesaparece();
        idUsuario = getIntent().getStringExtra("idUsuario");

        try {
            rutaUnoDesaparece_layoutView = new Activity_RutaUnoDesaparece_Layout(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(rutaUnoDesaparece_layoutView);
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
