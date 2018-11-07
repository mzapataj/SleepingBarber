package Logic;

import Graficos.Animation;
import Graficos.Imagen;
import Graficos.Lienzo;
import static Logic.Barberia.Log;
import Main.Main;
import java.awt.image.BufferedImage;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mzapataj
 */
public class Cliente extends Persona {

    public static boolean haSidoPausado;

    private int sillaAsignada = -1;
    private boolean atendido;
    public Semaphore turno;
    
    public Cliente(Barberia b, String nombreProceso) {
        super(b, nombreProceso);
        barberia = b;
        cargarAnimaciones("guy", 5);
        direccion = 0;
        currentAnimation = animaciones[0];
        atendido = false;
        turno = new Semaphore(0, true);
        //System.out.println(this.getName() + " llega a la barbería");
    }

    public void recibirCortarCabello() {
        
        barberia.sillas[sillaAsignada] = false;
        sillaAsignada = -1;

        currentAnimation.stop();
        setCurrentAnimation(animaciones[1]);
        currentAnimation.setLocation(625 + 35, 98 + 28);
        Barberia.Log(this.getName() + " está recibiendo un corte de cabello.");
        
        long delta = 0;
        while (true) {
            if (!barberia.getLienzo().paused) {
                try {
                    tiempoInicioAtendida = System.currentTimeMillis();
                    
                    //System.out.println("delta cliente: "+delta);
                    sleep(5050-delta);
                    if (Cliente.haSidoPausado) {
                        delta = Math.abs(Main.tiempoInicioPausa-tiempoInicioAtendida);
                        Cliente.haSidoPausado = false;
                    }else{
                        setAtendido(true);
                        break;
                    }
                    //System.out.println("delta cliente: "+delta);
                } catch (InterruptedException ex) {

                }               
            }
            int i = 0;
            System.out.println(getName());
            
        }
                
    }

    public void setSillaAsignada(int sillaAsignada) {
        this.sillaAsignada = sillaAsignada;
    }

    public int getSillaAsignada() {
        return sillaAsignada;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    @Override
    public void moveHandler(Lienzo lienzo) {

        if (direccion == 0) {
            if (500 - 150 * ((int) sillaAsignada / 4) > currentAnimation.y && currentAnimation.x == 450 - 17) {
                currentAnimation.stop();
                direccion = 3;
                animaciones[direccion].setLocation(currentAnimation.x, currentAnimation.y);
                setCurrentAnimation(animaciones[direccion]);
                currentAnimation.start();
            }

            if (sillaAsignada != -1 && currentAnimation.x < lienzo.sillas.get(sillaAsignada).x + 10 && currentAnimation.x > lienzo.sillas.get(sillaAsignada).x && currentAnimation.y < lienzo.sillas.get(sillaAsignada).y + 16) {
                currentAnimation.stop();
                direccion = 1;
                animaciones[direccion].setLocation(currentAnimation.x, currentAnimation.y);
                setCurrentAnimation(animaciones[direccion]);
                Log(getName() + " se siente en una silla de espera.");
                barberia.clientes.release();
                turno.release();
            }
        }

        if (direccion == 3 && currentAnimation.x < lienzo.sillas.get(sillaAsignada).x + 10 && currentAnimation.x > lienzo.sillas.get(sillaAsignada).x) {
            currentAnimation.stop();
            direccion = 0;
            animaciones[direccion].setLocation(currentAnimation.x, currentAnimation.y);
            setCurrentAnimation(animaciones[direccion]);
            currentAnimation.start();
        }
    }

    @Override
    public void run() {
        try {       
            barberia.llegadaCliente(this);
        } catch (InterruptedException ex) {
            //Log(getName() + " ha sido interrumpido.");
        }
    }
}
