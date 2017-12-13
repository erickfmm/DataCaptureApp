package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import model.Configuracion;

public class SeleccionaFigura extends Activity {

    Configuracion config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_figura);

        config = getIntent().getParcelableExtra("config");

        //Selecciona Cuadrado
        ImageButton cuadrado = (ImageButton) findViewById(R.id.ibCuadrado);
        cuadrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setFigura("cuadrado");

                Intent intent = new Intent(view.getContext(), SeleccionaRuta.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        //Selecciona Circulo
        ImageButton circulo = (ImageButton) findViewById(R.id.ibCirculo);
        circulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setFigura("circulo");

                Intent intent = new Intent(view.getContext(), SeleccionaRuta.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        //Selecciona Triangulo
        ImageButton triangulo = (ImageButton) findViewById(R.id.ibTriangulo);
        triangulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setFigura("triangulo");

                Intent intent = new Intent(view.getContext(), SeleccionaRuta.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        ImageButton rombo = (ImageButton) findViewById(R.id.ibRombo);
        rombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setFigura("rombo");

                Intent intent = new Intent(view.getContext(), SeleccionaRuta.class);
                intent.putExtra("config",config);
                view.getContext().startActivity(intent);
            }
        });

        //Fin OnCreate
    }
}
