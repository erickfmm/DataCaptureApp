package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import db.DataBaseManagerConfig;
import model.Configuracion;


public class SeleccionaDondeDesaparece extends Activity{

    Configuracion config;
    DataBaseManagerConfig managerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_desaparece);

        managerConfig = new DataBaseManagerConfig(this);

        config = getIntent().getParcelableExtra("config");

        Button op1 = (Button) findViewById(R.id.b2550);
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setDesaparece("2550");
                managerConfig.insertar(null, config.getNombre(), config.getFigura(), config.getRuta(), config.getDesaparece());
                Intent intent = new Intent(view.getContext(), SeleccionaEntrenamientoVelocidad.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        Button op2 = (Button) findViewById(R.id.b5075);
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setDesaparece("5075");
                managerConfig.insertar(null, config.getNombre(), config.getFigura(), config.getRuta(), config.getDesaparece());
                Intent intent = new Intent(view.getContext(), SeleccionaEntrenamientoVelocidad.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        Button op3 = (Button) findViewById(R.id.b75100);
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setDesaparece("75100");
                managerConfig.insertar(null, config.getNombre(), config.getFigura(), config.getRuta(), config.getDesaparece());
                Intent intent = new Intent(view.getContext(), SeleccionaEntrenamientoVelocidad.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });



    }
}