package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import model.Configuracion;


public class SeleccionaRuta extends Activity{

    Configuracion config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_ruta);

        config = getIntent().getParcelableExtra("config");

        ImageButton ruta1 = (ImageButton) findViewById(R.id.ibRuta1);
        ruta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setRuta("ruta1");

                Intent intent = new Intent(view.getContext(), SeleccionaDondeDesaparece.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        ImageButton ruta2 = (ImageButton) findViewById(R.id.ibRuta2);
        ruta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Ruta 2", Toast.LENGTH_SHORT).show();
            }
        });

        //Fin OnCreate
    }
}
