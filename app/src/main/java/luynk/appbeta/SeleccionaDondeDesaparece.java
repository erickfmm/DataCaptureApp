package luynk.appbeta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import model.Configuracion;


public class SeleccionaDondeDesaparece extends Activity{

    Configuracion config;

    NumberPicker des_inicio, des_final;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_desaparece);

        des_inicio = (NumberPicker) findViewById(R.id.picker_desaparece_inicio);
        des_final = (NumberPicker) findViewById(R.id.picker_desaparece_final);

        des_inicio.setMinValue(0);
        des_inicio.setMaxValue(99);

        des_final.setMinValue(0);
        des_final.setMaxValue(100);

        des_inicio.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                des_final.setMinValue(newVal+1);
            }
        });

        config = getIntent().getParcelableExtra("config");

        Button sel = (Button) findViewById(R.id.button_select);
        sel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                config.setDesapareceInicio(des_inicio.getValue());
                config.setDesapareceFinal(des_final.getValue());
                   Intent intent = new Intent(view.getContext(), SeleccionaEntrenamientoVelocidad.class);
                   intent.putExtra("config",config);
                   view.getContext().startActivity(intent);
               }

       });/*

        Button op1 = (Button) findViewById(R.id.b2550);
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.setDesaparece("2550");
                managerConfig.insertar(null, config.getNombre(), config.getFigura(), config.getRuta(), config.getDesaparece());

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
        });*/



    }
}