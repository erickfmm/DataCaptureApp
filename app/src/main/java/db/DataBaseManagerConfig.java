package db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import model.Configuracion;

public class DataBaseManagerConfig extends DataBaseManager{

    private static final String NOMBRE_TABLA = "configuracion";

    private static final String CN_ID = "_id";
    private static final String CN_NOMBRE = "nombre";
    private static final String CN_FIGURA = "figura";
    private static final String CN_RUTA = "ruta";
    private static final String CN_DESAPARECE = "desaparece";

    public static final String CREATE_TABLE = "create table " + NOMBRE_TABLA + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_NOMBRE + " text NOT NULL, "
            + CN_FIGURA + " text NOT NULL, "
            + CN_RUTA + " integer NOT NULL, "
            + CN_DESAPARECE + " integer NOT NULL"
            + ");";

    public DataBaseManagerConfig(Context context)
    {
        super(context);
    }


    @Override
    public void cerrar()
    {
        super.getDb().close();
    }

    private ContentValues generarContentValues(String id, String nombre, String figura, String ruta, String desaparece)
    {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_FIGURA, figura);
        valores.put(CN_RUTA, ruta);
        valores.put(CN_DESAPARECE, desaparece);

        return valores;
    }


    @Override
    public void insertar(String id, String nombre, String figura, String ruta, String desaparece)
    {
        super.getDb().insert(NOMBRE_TABLA,null, generarContentValues(id,nombre,figura,ruta,desaparece));
    }

    @Override
    public void actualizar(String id, String nombre, String figura, String ruta, String desaparece)
    {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_FIGURA, figura);
        valores.put(CN_RUTA, ruta);
        valores.put(CN_DESAPARECE, desaparece);

        String [] args = new String[]{id};

        super.getDb().update(NOMBRE_TABLA, valores, "_id=?", args);
    }

    @Override
    public void eliminar(String id)
    {
        super.getDb().delete(NOMBRE_TABLA, CN_ID +"=?", new String[]{id});
    }

    @Override
    public void eliminarTodo()
    {
        String query = "DELETE FROM "+ NOMBRE_TABLA+";";
        super.getDb().execSQL(query);
    }

    @Override
    public Cursor cargarCursor()
    {
        String [] columnas = new String[]{CN_ID, CN_NOMBRE, CN_FIGURA, CN_RUTA, CN_DESAPARECE};
        return super.getDb().query(NOMBRE_TABLA, columnas, null, null, null, null, null);
    }

    @Override
    public Boolean compruebaRegistro(String id)
    {
        boolean flag;

        String query = "Select * from "+ NOMBRE_TABLA + " WHERE " + CN_ID + "=" + id;
        Cursor resultSet =  super.getDb().rawQuery(query,null);

        flag = resultSet.getCount() > 0;

        return flag;
    }

    public List<Configuracion> getConfiguracionesList()
    {
        List<Configuracion> list = new ArrayList<>();

        Cursor cursor = cargarCursor();

        while (cursor.moveToNext()){
            Configuracion configuracion = new Configuracion();

            configuracion.setId(cursor.getString(0));
            configuracion.setNombre(cursor.getString(1));
            configuracion.setFigura(cursor.getString(2));
            configuracion.setRuta(cursor.getString(3));
            configuracion.setDesaparece(cursor.getString(4));

            list.add(configuracion);
        }

        return list;
    }
}
