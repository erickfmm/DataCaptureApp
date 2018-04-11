package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import model.Configuracion;

public class SeleccionaEntrenamientoVelocidad extends Activity{

    Configuracion config;
    NumberPicker entrenamientos, trials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_intentos);

        config = getIntent().getParcelableExtra("config");

        entrenamientos = (NumberPicker) findViewById(R.id.numEntrenamiento);

        trials = (NumberPicker) findViewById(R.id.numTrials);

        //Specify the maximum value/number of NumberPicker
        entrenamientos.setMinValue(1);
        trials.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        entrenamientos.setMaxValue(1000);
        trials.setMaxValue(1000);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        entrenamientos.setWrapSelectorWheel(false);
        trials.setWrapSelectorWheel(false);

        Button continuar = (Button) findViewById(R.id.btnEntrenamientoContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    config.setIntentos(entrenamientos.getValue());
                    config.setTrialNumber(trials.getValue());
                    Intent intent = new Intent(view.getContext(), SeleccionarVelocidad.class);
                    intent.putExtra("config",config);
                    view.getContext().startActivity(intent);
            }
        });

        //Fin OnCreate
    }
}
