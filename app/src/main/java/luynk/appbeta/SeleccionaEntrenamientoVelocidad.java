package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import db.DataBaseManagerConfig;
import model.Configuracion;

public class SeleccionaEntrenamientoVelocidad extends Activity{

    Configuracion config;
    NumberPicker entrenamientos, seconds;
    DataBaseManagerConfig managerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_velocidad);

        config = getIntent().getParcelableExtra("config");

        entrenamientos = (NumberPicker) findViewById(R.id.numEntrenamiento);

        seconds = (NumberPicker) findViewById(R.id.numVelocidad);

        //Specify the maximum value/number of NumberPicker
        entrenamientos.setMinValue(1);
        seconds.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        entrenamientos.setMaxValue(1000);
        seconds.setMaxValue(1000);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        entrenamientos.setWrapSelectorWheel(true);
        seconds.setWrapSelectorWheel(false);

        managerConfig = new DataBaseManagerConfig(this);

        Button continuar = (Button) findViewById(R.id.btnEntrenamientoContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //int speed = seconds.getValue();
                    //int entrenamiento = entrenamientos.getValue();
                    config.setSegundosVelocidad(seconds.getValue());
                    config.setIntentos(entrenamientos.getValue());
                    managerConfig.insertar(null, config.getNombre(), config.getIntentos(),
                            config.getSegundosVelocidad(), config.getDesapareceInicio(), config.getDesapareceFinal());
                    Intent intent = new Intent(view.getContext(), InsertarIdUsuario.class);
                    intent.putExtra("config",config);
                    //intent.putExtra("seconds", speed);
                    //intent.putExtra("entrenamiento", entrenamiento);
                    view.getContext().startActivity(intent);
            }
        });

        //Fin OnCreate
    }
}
