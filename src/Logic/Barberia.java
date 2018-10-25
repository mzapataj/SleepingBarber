package Logic;


import com.sun.corba.se.spi.transport.CorbaConnection;
import java.util.Random;
import java.util.concurrent.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mzapataj
 */
public class Barberia {
    
    Semaphore barberoListo;
    Semaphore sillasAccesibles;
    Semaphore clientes;
    Random rng;
    int sillasLibres;
    

    public Barberia(int tam) {
        sillasLibres = tam;
        barberoListo = new Semaphore(0, true);
        sillasAccesibles =  new Semaphore(1, true);
        clientes = new Semaphore(0, true);
        Log("Una nueva barbería ha sido abierta al público.");
    }
    
    public void atenderCliente(Barbero barber) throws InterruptedException{
        
        if (clientes.availablePermits() == 0) {
            Log(barber.getName()+ " se duerme");
            barber.setEstaDormido(true);
        }
        
        clientes.acquire();
        
        if (barber.estaDormido()) {
            Log(barber.getName() + " se despierta.");
            barber.setEstaDormido(false);
        }
        
        sillasAccesibles.acquire();
        sillasLibres += 1;
        barberoListo.release();
        sillasAccesibles.release();
        barber.cortarCabello();
        
    
    }
    
    public void llegadaCliente(Cliente customer) throws InterruptedException{
        sillasAccesibles.acquire();
        if (sillasLibres > 0) {
            Log(customer.getName() + " se siente en una silla de espera.");
            sillasLibres -= 1;
            clientes.release();
            sillasAccesibles.release();
            barberoListo.acquire();
            customer.recibirCortarCabello();
            
        }else{
            sillasAccesibles.release();
            Log("No hay sillas libres. "+ customer.getName() + " se va de la barbería.");
        }
            //System.out.println(nombre + " se va de la barbería.");
    }
    
    public static void Log(String mensaje){
        System.out.println(mensaje);
    }
    
}
