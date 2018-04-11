package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import model.Configuracion;

public class ExplicacionPrincipal extends Activity {

    String idUsuario, aux, rootPathUser;
    Configuracion config;
    int entrenamientos, contador_entrenamientos, contador_ruta, contador_trials;
    int[] chosen_ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacion_principal);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        entrenamientos = Integer.parseInt(config.getIntentos());
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);
        contador_ruta = getIntent().getIntExtra("contador_ruta", 0);
        rootPathUser = getIntent().getStringExtra("rootPathUser");

        chosen_ruta = getIntent().getIntArrayExtra("chosen_ruta");
        contador_trials = getIntent().getIntExtra("contador_trials", 0);
        aux = "principal";

        Button continuar = (Button) findViewById(R.id.btExPrincipal);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Contador.class);
                intent.putExtra("config",config);
                intent.putExtra("idUsuario", idUsuario);
                //intent.putExtra("points", points);
                intent.putExtra("entrenamiento", entrenamientos);
                intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                intent.putExtra("contador_ruta", contador_ruta);
                intent.putExtra("chosen_ruta", chosen_ruta);
                intent.putExtra("contador_trials", contador_trials);
                intent.putExtra("rootPathUser", rootPathUser);
                intent.putExtra("aux", aux);
                view.getContext().startActivity(intent);
            }
        });
    }
}
