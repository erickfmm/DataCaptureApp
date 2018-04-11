package model;


import android.os.Parcel;
import android.os.Parcelable;

public class Configuracion implements Parcelable{
    private String id;
    private String nombre;
    private String intentos;
    private String trialNumber;
    private String segundosVelocidad;
    private String desapareceInicio;
    private String desapareceFinal;

    public Configuracion() {
    }

    public Configuracion(String id, String nombre, String intentos, String segundosVelocidad,
                         String desapareceInicio, String desapareceFinal, String trialNumber) {
        this.id = id;
        this.nombre = nombre;
        this.intentos = intentos;
        this.trialNumber = trialNumber;
        this.segundosVelocidad = segundosVelocidad;
        this.desapareceInicio = desapareceInicio;
        this.desapareceFinal = desapareceFinal;
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

    public String getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = Integer.toString(intentos);
    }

    public String getSegundosVelocidad() {
        return segundosVelocidad;
    }

    public void setSegundosVelocidad(int segundosVelocidad) {
        this.segundosVelocidad = Integer.toString(segundosVelocidad);
    }

    public String getDesapareceInicio() {
        return desapareceInicio;
    }

    public void setDesapareceInicio(int desapareceInicio) {
        this.desapareceInicio = Integer.toString(desapareceInicio);
    }

    public String getDesapareceFinal() {
        return desapareceFinal;
    }

    public void setDesapareceFinal(int desapareceFinal) {
        this.desapareceFinal = Integer.toString(desapareceFinal);
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(int trialNumber) {
        this.trialNumber = Integer.toString(trialNumber);
    }



    //Parcelable part
    public Configuracion(Parcel in){
        String[] data= new String[7];

        in.readStringArray(data);
        this.id = data[0];
        this.nombre = data[1];
        this.intentos = data[2];
        this.segundosVelocidad = data[3];
        this.desapareceInicio = data[4];
        this.desapareceFinal = data[5];
        this.trialNumber = data[6];
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
                this.intentos,
                this.segundosVelocidad,
                this.desapareceInicio,
                this.desapareceFinal,
                this.trialNumber
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
