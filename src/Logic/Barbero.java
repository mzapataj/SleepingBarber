package Logic;

import Graficos.Animation;
import Graficos.Imagen;
import Graficos.Lienzo;
import static Logic.Barberia.Log;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mzapataj
 */
public class Barbero extends Persona {

    boolean estaDormido;
    public static boolean haSidoPausado;
    
    public Barbero(Barberia b, String nombreProceso) {

        super(b, nombreProceso);
        estaDormido = true;
        cargarAnimaciones("barber", 0); 
        BufferedImage[] up = {new Imagen("barberUp2").getImagen(), new Imagen("barberUp3").getImagen()};
        animaciones[0] = new Animation(up, 10, 0, -1*0);
        animaciones[0].setLocation(660, 155);
        animaciones[1].setLocation(660, 155);
        animaciones[2].setLocation(660, 155);
        animaciones[3].setLocation(660, 155);

    }

    public boolean estaDormido() {
        return estaDormido;
    }

    public void setEstaDormido(boolean estaDormido) {
        this.estaDormido = estaDormido;
    }

    public void cortarCabello() {
        setCurrentAnimation(getAnimaciones()[0]);
        getCurrentAnimation().start();
        Barberia.Log(getName() + " está cortando el cabello.");
        long delta = 0;
        
     /*   do{
            
            
        }while(getAnimaciones()[1].equals(getCurrentAnimation()));
       */ 
        //while (true) {
            //if (!barberia.getLienzo().paused) {
                try {
                    /*System.out.println("delta barbero: "+ delta);
                    tiempoInicioAtendida = System.currentTimeMillis();
                    sleep(5000-delta);*/
                    sleep(5000);
                    setCurrentAnimation(getAnimaciones()[1]);
                    /*if (Barbero.haSidoPausado) {
                        delta = Math.abs(Main.tiempoInicioPausa-tiempoInicioAtendida);
                        Barbero.haSidoPausado = false;
                    }else{
                        setCurrentAnimation(getAnimaciones()[1]);
                        break;
                    }
                    System.out.println("delta barbero: "+ delta);
                    */
                } catch (InterruptedException ex) {
                    
                }
                
            //}
            
        //}

    }

    @Override
    public void run() {
        while (true) {
            try {
                barberia.atenderCliente(this);
            } catch (InterruptedException ex) {
                
            }
        }
    }

    @Override
    public void moveHandler(Lienzo lienzo) {

        if (!lienzo.clientes.isEmpty()) {
            if (estaDormido) {
                Log(getName() + " se despierta.");
                setCurrentAnimation(getAnimaciones()[direccion]);
                getCurrentAnimation().start();
                setEstaDormido(false);
            }
        } else {
            if (!estaDormido) {
                Log(getName() + " se duerme");
                setCurrentAnimation(getAnimaciones()[direccion]);
                getCurrentAnimation().stop();
                setEstaDormido(true);
            }
        }

    }
}
