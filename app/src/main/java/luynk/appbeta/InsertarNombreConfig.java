package luynk.appbeta;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Configuracion;

public class InsertarNombreConfig extends Activity{

    EditText nombreConfig;
    Configuracion config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_nombre_config);

        nombreConfig = (EditText) findViewById(R.id.etNombreConfig);

        //config = getIntent().getParcelableExtra("config");

        Button continuar = (Button) findViewById(R.id.btnNombreConfigContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombreConfig.getText() == null || nombreConfig.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "You must enter a name", Toast.LENGTH_SHORT).show();
                }else {
                    String nombre = nombreConfig.getText().toString();

                    config = new Configuracion(null, nombre, null, null, null, null, null);

                    //Intent intent = new Intent(view.getContext(), SeleccionaFigura.class);
                    Intent intent = new Intent(view.getContext(), SeleccionaDondeDesaparece.class);
                    intent.putExtra("config",config);
                    view.getContext().startActivity(intent);
                    finish();
                }
            }
        });
    }
}
