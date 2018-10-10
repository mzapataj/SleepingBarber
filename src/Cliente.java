
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
public class Cliente extends Thread {
    
    Barberia barberia;
    
    public Cliente(Barberia b, String nombreProceso){
        super(nombreProceso);
        barberia = b;
        System.out.println(this.getName()+ " llega a la barberia");
    }
    @Override
    public void run(){
        try {
            barberia.llegadaCliente(this.getName());
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
