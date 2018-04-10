package luynk.appbeta;

import java.util.ArrayList;

public class Ruta {
    public static ArrayList<Puntos> getRuta(int ruta, int seconds, int screenWidth, int screenHeight){
        double percent = 100/seconds; //Percent of screen in 1 second
        double speed = percent/6000;
        double t_aux = 1 / (seconds*60);
        int dim = (int) seconds * 60;
        boolean flag = false;
        double yvalue;
        Double aux = screenWidth * speed;

        float x_dir = aux.floatValue();

        System.out.println("width: "+screenWidth+"\theight: "+screenHeight);
        System.out.println("ruta: "+ruta);
        ruta = ruta % 4;
        System.out.println("new ruta mod4: "+ruta);
        ArrayList<Puntos> points = new ArrayList<>();
        float elemento_x=0, elemento_y=0;
        for (int i=0; i < dim; i++){

            elemento_x = elemento_x + x_dir;
            switch (ruta){
                case 0:
                    yvalue = Math.sin(2 * Math.PI * (0.99 / 2 * (t_aux * t_aux)));
                    elemento_y = (float) (screenHeight / 2 + (-1 * yvalue * screenHeight * 0.4));
                    break;
                case 1:
                    yvalue = Math.sin(2 * Math.PI * (0.99 / 2 * (t_aux * t_aux)));
                    elemento_y = (float) (screenHeight / 2 + (yvalue * screenHeight * 0.4));
                    break;
                case 2:
                    yvalue = Math.sin(2 * Math.PI * (1.5 / 2 * (t_aux * t_aux)));
                    elemento_y = (float) (screenHeight / 2 + (-1 * yvalue * screenHeight * 0.4));
                    break;
                default:
                    yvalue = Math.sin(2 * Math.PI * (1.5 / 2 * (t_aux * t_aux)));
                    elemento_y = (float) (screenHeight / 2 + (yvalue * screenHeight * 0.4));
                    break;
            }

            t_aux = t_aux + (1 / (seconds * 60));
            points.add(new Puntos(elemento_x, elemento_y));

            if(i == dim-1){
                flag = true;
            }
        }
        return points;
    }

    public static String getFigure(int ruta){
        ruta = ruta %4;
        String figura;
        switch (ruta){
            case 1:
                figura = "square";
                break;
            case 2:
                figura = "circle";
                break;
            case 3:
                figura = "triangle";
                break;
            default:
                figura = "rhombus";
                break;
        }
        //System.out.println("ruta: "+ruta+"\tfigura: "+figura);
        return figura;
    }
}
