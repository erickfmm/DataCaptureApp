package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import model.Configuracion;

public class ExplicacionEntrenamiento extends Activity {

    String idUsuario, aux;
    Configuracion config;
    int contador_entrenamientos;
    private ArrayList<Puntos> points = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicacion_entrenamiento);

        config = getIntent().getParcelableExtra("config");
        idUsuario = getIntent().getStringExtra("idUsuario");
        points = (ArrayList<Puntos>) getIntent().getSerializableExtra("points");
        //entrenamientos = Integer.parseInt(config.getIntentos());// getIntent().getIntExtra("entrenamiento", 0);
        contador_entrenamientos = getIntent().getIntExtra("contador_entrenamientos", 0);

        aux = "entrenamiento";

        Button continuar = (Button) findViewById(R.id.btExplicacion);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), Contador.class);
                        intent.putExtra("config",config);
                        intent.putExtra("idUsuario", idUsuario);
                        intent.putExtra("points", points);
                        //intent.putExtra("entrenamiento", entrenamientos);
                        intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                        intent.putExtra("aux", aux);
                        view.getContext().startActivity(intent);
            }
        });
    }
}
