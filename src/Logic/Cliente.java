package Logic;

import Graficos.Lienzo;
import static Logic.Barberia.Log;
import java.awt.Container;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javax.swing.JLabel;

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
    public JLabel label;
    
    public static int clientesIdos = 0;
       
    
    public Cliente(Barberia b, String nombreProceso, ResourceBundle resourceBundle) {
        super(b, nombreProceso, resourceBundle);
        barberia = b;
        cargarAnimaciones("guy", 5);
        direccion = 0;
        currentAnimation = animaciones[0];
        atendido = false;               
        
        label = new JLabel(nombreProceso.substring(8));
        turno = new Semaphore(0, true);
        
        //System.out.println(this.getName() + " llega a la barbería");
    }

    public void recibirCortarCabello() {
        
        barberia.sillas[sillaAsignada] = false;
        sillaAsignada = -1;

        currentAnimation.stop();
        setCurrentAnimation(animaciones[1]);
        currentAnimation.setLocation(625 + 35, 98 + 28);        
        Barberia.Log(String.format( messages.getString("receive-cut-hair"), this.getName()));
        
        long delta = 0;
        
        try {
            tiempoInicioAtendida = System.currentTimeMillis();

            sleep(5050);
            setAtendido(true);
            removerLabel();
            Cliente.clientesIdos++;                                       
        } catch (InterruptedException ex) {

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
    
    public void removerLabel(){
        Container parent = label.getParent();
        parent.remove(label);
        parent.validate();
        parent.repaint();

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
                
                Barberia.Log(String.format( messages.getString("wait-chair"), getName()));                                
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
        }
    }
}
