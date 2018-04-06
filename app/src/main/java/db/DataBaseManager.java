package db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataBaseManager {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public void cerrar(){
        db.close();
    }

    abstract public void insertar(String id, String nombre, String intentos, String segundosVelocidad,
                                  String desapareceInicio, String desapareceFinal);
    abstract public void actualizar(String id, String nombre, String intentos, String segundosVelocidad,
                                    String desapareceInicio, String desapareceFinal);

    abstract public void eliminar(String id);
    abstract public void eliminarTodo();
    abstract public Cursor cargarCursor();
    abstract public Boolean compruebaRegistro(String id);

    //Gets y Sets

    public DbHelper getHelper() {
        return helper;
    }

    public void setHelper(DbHelper helper) {
        this.helper = helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
