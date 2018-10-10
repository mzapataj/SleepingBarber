
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
    int sillasLibres;

    public Barberia(int tam) {
        sillasLibres = tam;
        barberoListo = new Semaphore(1, true);
        sillasAccesibles =  new Semaphore(1, true);
        clientes = new Semaphore(0, true);
        System.out.println("Una nueva barbería ha sido abierta al público.");
    }
    
    public void atenderCliente(String nombre) throws InterruptedException{
        clientes.acquire();
        System.out.println(nombre + " se despierta.");
        sillasAccesibles.acquire();
        sillasLibres += 1;
        System.out.println(nombre + " está listo para cortar el pelo.");
        barberoListo.release();
        sillasAccesibles.release();
        //System.out.println(nombre + " ha terminado de cortar el pelo.");
        System.out.println(nombre + " está cortando el pelo....");
    }
    
    public void llegadaCliente(String nombre) throws InterruptedException{
        sillasAccesibles.acquire();
        if (sillasLibres > 0) {
            System.out.println(nombre + " se siente en una silla de espera.");
            sillasLibres -= 1;
            clientes.release();
            sillasAccesibles.release();
            barberoListo.acquire();
            System.out.println(nombre + " pasa a ser atendido.");
        }else{
            sillasAccesibles.release();
            System.out.println(nombre + " se va de la barbería.");
        }
            //System.out.println(nombre + " se va de la barbería.");
    }
    
}
