package luynk.appbeta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.res.Resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.Configuracion;


public class InsertarIdUsuario extends Activity {

    EditText idUsuario;
    Configuracion config;
    double seconds;
    int entrenamiento, contador_entrenamientos, contador_ruta, contador_trials;
    int[] chosen_ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertaridusuario);

        idUsuario = (EditText) findViewById(R.id.etIdUsuario);

        config = getIntent().getParcelableExtra("config");

        seconds = Integer.parseInt(config.getSegundosVelocidad());
        entrenamiento = Integer.parseInt(config.getIntentos());
        contador_entrenamientos = 0;
        contador_ruta = 0;
        chosen_ruta = Ruta.chooseRoutes();
        System.out.println("----------------------------------------------------------");
        System.out.println(chosen_ruta[0]+", "+chosen_ruta[1]+", "+chosen_ruta[2]);
        System.out.println("----------------------------------------------------------");
        contador_trials = 0; //Integer.parseInt(config.getTrialNumber());



        Button continuar = (Button) findViewById(R.id.btIdUsuario);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idUsuario.getText() == null || idUsuario.getText().toString().equals("")) {
                    Toast.makeText(view.getContext(), "You must insert an ID.", Toast.LENGTH_SHORT).show();
                }else {
                    String id = idUsuario.getText().toString();

                    Date todayDate = Calendar.getInstance().getTime();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    String todayString = formatter.format(todayDate);

                    //Crear carpeta para archivos la primera ves en el dispositivo
                    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataCaptureApp/";

                    File root = new File(rootPath);
                    if (!root.exists()) {
                        root.mkdirs();
                    }

                    //crear carpeta de usuario
                    String rootPathUser = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DataCaptureApp/"+id+"_"+todayString+"/";
                    File rootAux = new File(rootPathUser);
                    if (!rootAux.exists()) {
                        rootAux.mkdirs();
                    }

                    Intent intent = new Intent(view.getContext(), ExplicacionEntrenamiento.class);
                    intent.putExtra("config",config);
                    intent.putExtra("idUsuario", id);
                    intent.putExtra("contador_entrenamientos", contador_entrenamientos);
                    intent.putExtra("contador_ruta", contador_ruta);
                    intent.putExtra("chosen_ruta", chosen_ruta);
                    intent.putExtra("rootPathUser", rootPathUser);
                    intent.putExtra("contador_trials", contador_trials);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }
}
