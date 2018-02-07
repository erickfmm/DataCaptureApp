package luynk.appbeta;


import java.io.Serializable;

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
}

