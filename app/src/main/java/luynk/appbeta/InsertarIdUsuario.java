package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.res.Resources;

import java.util.ArrayList;

import model.Configuracion;


public class InsertarIdUsuario extends Activity {

    EditText idUsuario;
    Configuracion config;
    double speed, percent, seconds, t_aux, yvalue;
    float elemento_x=0, elemento_y=0;
    boolean flag;
    int entrenamiento, contador_entrenamientos;

    private ArrayList<Puntos> points = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertaridusuario);

        idUsuario = (EditText) findViewById(R.id.etIdUsuario);

        config = getIntent().getParcelableExtra("config");

        seconds =  getIntent().getIntExtra("seconds", 0);
        entrenamiento = getIntent().getIntExtra("entrenamiento", 0);
        contador_entrenamientos = 0;

        percent = 100/seconds; //Percent of screen in 1 second
        speed = percent/6000;
        t_aux = 1 / (seconds*60);
        int dim = (int) seconds * 60;
        flag = false;

        Double aux = getScreenWidth() * speed;

        float x_dir = aux.floatValue();

        String ruta_aux = config.getRuta();

        //Calcular movimiento del objeto

        for (int i=0; i < dim; i++){

            elemento_x = elemento_x + x_dir;

            if (ruta_aux.contains("route1")){
                yvalue = Math.sin(2 * Math.PI * (0.99 / 2 * (t_aux * t_aux)));
                elemento_y = (float) (getScreenHeight() / 2 + (-1 * yvalue * getScreenHeight() * 0.4));
            } else if (ruta_aux.contains("route2")){
                yvalue = Math.sin(2 * Math.PI * (0.99 / 2 * (t_aux * t_aux)));
                elemento_y = (float) (getScreenHeight() / 2 + (yvalue * getScreenHeight() * 0.4));
            } else if (ruta_aux.contains("route3")){
                yvalue = Math.sin(2 * Math.PI * (1.5 / 2 * (t_aux * t_aux)));
                elemento_y = (float) (getScreenHeight() / 2 + (-1 * yvalue * getScreenHeight() * 0.4));
            } else{
                yvalue = Math.sin(2 * Math.PI * (1.5 / 2 * (t_aux * t_aux)));
                elemento_y = (float) (getScreenHeight() / 2 + (yvalue * getScreenHeight() * 0.4));
            }

            t_aux = t_aux + (1 / (seconds * 60));
            points.add(new Puntos(elemento_x, elemento_y));

            if(i == dim-1){
                flag = true;
            }
        }

        Button continuar = (Button) findViewById(R.id.btIdUsuario);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idUsuario.getText() == null || idUsuario.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "You must insert an ID.", Toast.LENGTH_SHORT).show();
                }else {
                    String id = idUsuario.getText().toString();
                    if(flag){
                        Intent intent = new Intent(view.getContext(), ExplicacionEntrenamiento.class);
                        intent.putExtra("config",config);
                        intent.putExtra("idUsuario", id);
                        intent.putExtra("points", points);
                        intent.putExtra("entrenamiento", entrenamiento);
                        intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                        view.getContext().startActivity(intent);
                    }
                }
            }
        });
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
