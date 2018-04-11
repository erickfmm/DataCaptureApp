package luynk.appbeta;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import db.DataBaseManagerConfig;
import model.Configuracion;

public class SeleccionarVelocidad extends Activity {

    Configuracion config;
    NumberPicker velocidad;
    DataBaseManagerConfig managerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_velocidad);

        config = getIntent().getParcelableExtra("config");

        velocidad = (NumberPicker) findViewById(R.id.velocidad_config);

        //Specify the maximum value/number of NumberPicker
        velocidad.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        velocidad.setMaxValue(1000);
        //Gets whether the selector wheel wraps when reaching the min/max value.
        velocidad.setWrapSelectorWheel(false);

        managerConfig = new DataBaseManagerConfig(this);

        Button continuar = (Button) findViewById(R.id.select_button);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setSegundosVelocidad(velocidad.getValue());
                managerConfig.insertar(null, config.getNombre(), config.getIntentos(),
                        config.getSegundosVelocidad(), config.getDesapareceInicio(), config.getDesapareceFinal(), config.getTrialNumber());
                Intent intent = new Intent(view.getContext(), SeleccionaConfiguracion.class);
                view.getContext().startActivity(intent);
            }
        });
    }

}
