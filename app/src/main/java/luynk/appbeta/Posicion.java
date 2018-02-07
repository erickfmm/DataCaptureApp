package luynk.appbeta;


import java.io.Serializable;

public class Posicion implements Serializable{
        private String x;
        private String y;

        public Posicion(String x, String y) {
            this.x = "x:" + x;
            this.y = "y:" + y;
        }

    public String getY() {
        return y;
    }
}
