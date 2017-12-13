package luynk.appbeta;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

import adaptadores.CartaConfiguracionAdapter;
import db.DataBaseManagerConfig;
import model.Configuracion;

public class SeleccionaConfiguracion extends Activity{

    private DataBaseManagerConfig managerConfig;
    private RecyclerView recycler;
    private CartaConfiguracionAdapter adapter;
    private List<Configuracion> listaItemsConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecciona_configuracion);

        managerConfig = new DataBaseManagerConfig(this);
        listaItemsConfig = managerConfig.getConfiguracionesList();

        inicializarRecycler();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirmar Eliminación");
        builder.setMessage("Seguro que desea eliminar todas las configuraciones guardadas");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        managerConfig.eliminarTodo();
                        recargarRecycler();
                    }
                });
        builder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        Button btnNuevaConfig = (Button) findViewById(R.id.btnNuevaConfig);
        btnNuevaConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pasar a actividad crear Configuración
                Intent Intent = new Intent(view.getContext(), InsertarNombreConfig.class);
                view.getContext().startActivity(Intent);
            }
        });

        Button btnEliminarTodo = (Button) findViewById(R.id.btnEliminarTodo);
        btnEliminarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void  inicializarRecycler()
    {
        //Obtener recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        //Usar administrador para LinearLayout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        //Crear nuevo adaptador
        adapter = new CartaConfiguracionAdapter(this, listaItemsConfig);
        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void recargarRecycler()
    {
        //Cargar Datos
        listaItemsConfig = managerConfig.getConfiguracionesList();
        //Crear nuevo adaptador
        adapter = new CartaConfiguracionAdapter(this, listaItemsConfig);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onDestroy()
    {
        managerConfig.cerrar();
        super.onDestroy();
    }

}
