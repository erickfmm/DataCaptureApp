package model;


import android.os.Parcel;
import android.os.Parcelable;

public class Configuracion implements Parcelable{
    private String id;
    private String nombre;
    private String figura;
    private String ruta;
    private String desaparece;

    public Configuracion() {
    }

    public Configuracion(String id, String nombre, String figura, String ruta, String desaparece) {
        this.id = id;
        this.nombre = nombre;
        this.figura = figura;
        this.ruta = ruta;
        this.desaparece = desaparece;
    }


    //GETs y SETs
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFigura() {
        return figura;
    }

    public void setFigura(String figura) {
        this.figura = figura;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDesaparece() {
        return desaparece;
    }

    public void setDesaparece(String desaparece) {
        this.desaparece = desaparece;
    }


    //Parcelable part
    public Configuracion(Parcel in){
        String[] data= new String[5];

        in.readStringArray(data);
        this.id = data[0];
        this.nombre = data[1];
        this.figura = data[2];
        this.ruta = data[3];
        this.desaparece = data[4];
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

        dest.writeStringArray(new String[]{
                this.id,
                this.nombre,
                this.figura,
                this.ruta,
                this.desaparece
        });
    }

    public static final Parcelable.Creator<Configuracion> CREATOR = new Parcelable.Creator<Configuracion>() {

        @Override
        public Configuracion createFromParcel(Parcel source) {

            return new Configuracion(source);  //using parcelable constructor
        }

        @Override
        public Configuracion[] newArray(int size) {
            return new Configuracion[size];
        }
    };


}
