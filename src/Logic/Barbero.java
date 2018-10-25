package Logic;


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
    boolean estaDormido;
    
    public Barbero(Barberia b, String nombreProceso){
        super(nombreProceso);
        barberia = b;
        estaDormido = true;
        System.out.println(this.getName()+" llega a la barbería.");    
    }
    
    public boolean estaDormido() {
        return estaDormido;
    }
    
    public void setEstaDormido(boolean estaDormido) {
        this.estaDormido = estaDormido;
    }

    public void cortarCabello(){
        Barberia.Log(getName()+ " está cortando el cabello.");
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
        }
    }
    
    @Override
    public void run(){
        while (true){
            try {
                barberia.atenderCliente(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Barbero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
