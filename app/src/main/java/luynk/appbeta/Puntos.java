package luynk.appbeta;


import java.io.Serializable;
import java.util.ArrayList;

public class Puntos implements Serializable{
        private float x;
        private float y;

        public Puntos(float x, float y) {
            this.x = x;
            this.y = y;
        }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static String toCSV(ArrayList<Puntos> p){
            String csv ="x,y\n";
            for(int i = 0; i<p.size(); i++){
                Puntos punto = p.get(i);
                csv += Float.toString(punto.getX()) + ","+Float.toString(punto.getY())+"\n";
            }
            return csv;
    }
}

