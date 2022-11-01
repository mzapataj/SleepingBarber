package Logic;

import Graficos.Animation;
import Graficos.Imagen;
import Graficos.Lienzo;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jorge
 */
public abstract class Persona extends Thread {

    Barberia barberia;
    protected ResourceBundle messages;
    protected Animation[] animaciones = new Animation[4];
    protected Animation currentAnimation;
    public long tiempoInterrupcion;
    public long tiempoInicioAtendida;
    /**
     * 0 = <b>"arriba"</b>, 1 = <b>"abajo"</b>, 2 = <b>"derecha"</b>, 3 =
     * <b>"izquierda"</b>
     *
     */
    byte direccion;

    public Persona(Barberia b, String nombreProceso, ResourceBundle resourceBundle ) {

        super(nombreProceso);
        barberia = b;
        messages = resourceBundle;
        Barberia.Log( String.format( messages.getString("arrive-barbery") , this.getName()));

    }

    public void cargarAnimaciones(String tipo, int vel) {
        BufferedImage[] up = {new Imagen(tipo + "Up1").getImagen(), new Imagen(tipo + "Up2").getImagen(), new Imagen(tipo + "Up3").getImagen()};
        BufferedImage[] down = {new Imagen(tipo + "Down1").getImagen(), new Imagen(tipo + "Down2").getImagen(), new Imagen(tipo + "Down3").getImagen()};
        BufferedImage[] right = {new Imagen(tipo + "Right1").getImagen(), new Imagen(tipo + "Right2").getImagen(), new Imagen(tipo + "Right3").getImagen()};
        BufferedImage[] left = {new Imagen(tipo + "Left1").getImagen(), new Imagen(tipo + "Left2").getImagen(), new Imagen(tipo + "Left3").getImagen()};

        animaciones[0] = new Animation(up, 10, 0, -1*vel);
        animaciones[1] = new Animation(down, 10, 0, vel);
        animaciones[2] = new Animation(right, 10, vel, 0);
        animaciones[3] = new Animation(left, 10, -1*vel, 0);
    }

    public Animation[] getAnimaciones() {
        return animaciones;
    }

    public byte getDireccion() {
        return direccion;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public void setDireccion(byte direccion) {
        this.direccion = direccion;
    }

    public abstract void moveHandler(Lienzo lienzo);

    @Override
    public abstract void run();
    
}
