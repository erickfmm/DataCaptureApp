package luynk.appbeta;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                //estamos bien!
            } else {
                requestPermission();
            }
        }

        setContentView(R.layout.activity_selecciona_configuracion);

        managerConfig = new DataBaseManagerConfig(this);
        listaItemsConfig = managerConfig.getConfiguracionesList();

        inicializarRecycler();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirm DELETE");
        builder.setMessage("Are you sure you want to DELETE ALL saved configurations?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        managerConfig.eliminarTodo();
                        recargarRecycler();
                    }
                });
        builder.setNegativeButton("Cancel",
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

    protected boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    protected void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save data. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //estamos bien
                } else {
                    // permission denied, boo!
                }
                break;
        }
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2); // new LinearLayoutManager(this);
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
