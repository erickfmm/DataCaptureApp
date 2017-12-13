package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Configuracion;


public class InsertarIdUsuario extends Activity {

    EditText idUsuario;
    Configuracion config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertaridusuario);

        idUsuario = (EditText) findViewById(R.id.etIdUsuario);

        config = getIntent().getParcelableExtra("config");

        Button continuar = (Button) findViewById(R.id.btIdUsuario);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idUsuario.getText() == null || idUsuario.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "Debe insertar un Id", Toast.LENGTH_SHORT).show();
                }else {
                    String id = idUsuario.getText().toString();
                    Intent intent = new Intent(view.getContext(), RutaUno.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", id);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }
}
