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
    private static final String CN_INTENTOS = "intentos";
    private static final String CN_SEGUNDOSVELOCIDAD = "velocidad";
    private static final String CN_DESAPARECEINICIO = "desapareceinicio";
    private static final String CN_DESAPARECEFINAL = "desaparecefinal";
    private static final String CN_TRIALNUMBER = "trialNumber";

    public static final String CREATE_TABLE = "create table " + NOMBRE_TABLA + " ("
            + CN_ID + " integer PRIMARY KEY AUTOINCREMENT, "
            + CN_NOMBRE + " text NOT NULL, "
            + CN_INTENTOS + " integer NOT NULL, "
            + CN_SEGUNDOSVELOCIDAD + " integer NOT NULL, "
            + CN_DESAPARECEINICIO + " integer NOT NULL,"
            + CN_DESAPARECEFINAL + " integer NOT NULL,"
            + CN_TRIALNUMBER + " integer NOT NULL"
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

    private ContentValues generarContentValues(String id, String nombre, String intentos, String segundosVelocidad,
                                               String desapareceInicio, String desapareceFinal, String trialNumber)
    {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_INTENTOS, intentos);
        valores.put(CN_SEGUNDOSVELOCIDAD, segundosVelocidad);
        valores.put(CN_DESAPARECEINICIO, desapareceInicio);
        valores.put(CN_DESAPARECEFINAL, desapareceFinal);
        valores.put(CN_TRIALNUMBER, trialNumber);

        return valores;
    }


    @Override
    public void insertar(String id, String nombre, String intentos, String segundosVelocidad,
                         String desapareceInicio, String desapareceFinal, String trialNumber)
    {
        super.getDb().insert(NOMBRE_TABLA,null, generarContentValues(id,nombre,intentos,segundosVelocidad,desapareceInicio,desapareceFinal, trialNumber));
    }

    @Override
    public void actualizar(String id, String nombre, String intentos, String segundosVelocidad,
                           String desapareceInicio, String desapareceFinal, String trialNumber)
    {
        ContentValues valores = new ContentValues();
        valores.put(CN_ID, id);
        valores.put(CN_NOMBRE, nombre);
        valores.put(CN_INTENTOS, intentos);
        valores.put(CN_SEGUNDOSVELOCIDAD, segundosVelocidad);
        valores.put(CN_DESAPARECEINICIO, desapareceInicio);
        valores.put(CN_DESAPARECEFINAL, desapareceFinal);
        valores.put(CN_TRIALNUMBER, trialNumber);

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
        String [] columnas = new String[]{CN_ID, CN_NOMBRE, CN_INTENTOS, CN_SEGUNDOSVELOCIDAD, CN_DESAPARECEINICIO, CN_DESAPARECEFINAL, CN_TRIALNUMBER};
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
            configuracion.setIntentos(Integer.parseInt(cursor.getString(2)));
            configuracion.setSegundosVelocidad(Integer.parseInt(cursor.getString(3)));
            configuracion.setDesapareceInicio(Integer.parseInt(cursor.getString(4)));
            configuracion.setDesapareceFinal(Integer.parseInt(cursor.getString(5)));
            configuracion.setTrialNumber(Integer.parseInt(cursor.getString(6)));

            list.add(configuracion);
        }

        return list;
    }
}
