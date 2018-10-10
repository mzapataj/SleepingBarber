
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

public class Barbero extends Thread{
    
    Barberia barberia;
    
    public Barbero(Barberia b, String nombreProceso){
        super(nombreProceso);
        barberia = b;
        System.out.println(this.getName()+" llega a la barbería.");
    }
    @Override
    public void run(){
        while (true){
            try {
                barberia.atenderCliente(this.getName());
            } catch (InterruptedException ex) {
                Logger.getLogger(Barbero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
