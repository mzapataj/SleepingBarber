package Logic;


import Graficos.Animation;
import Graficos.Imagen;
import Graficos.Lienzo;
import com.sun.corba.se.spi.transport.CorbaConnection;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
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
    public List<Imagen> spriteSillas;
    int sillasLibres;
    private Lienzo lienzo;
    boolean[] sillas; 

    public Barberia(int tam) {
        
        sillasLibres = tam;
        barberoListo = new Semaphore(0, true);
        sillasAccesibles =  new Semaphore(1, true);
        clientes = new Semaphore(0, true);
        spriteSillas = new CopyOnWriteArrayList<>();
        generateSeatsSprite();
        Log("Una nueva barbería ha sido abierta al público.");
        sillas = new boolean[tam];
        
    }
    
    public Lienzo getLienzo() {
        return lienzo;        
    }

    public void setLienzo(Lienzo lienzo) {
        this.lienzo = lienzo;
    }

    public void generateSeatsSprite(){
        
        for (int i = 0; i <= (int)sillasLibres/ 4; i++) {
            for (int j = 0; j < 4 && (4*i+j)< sillasLibres; j++) {
                spriteSillas.add(new Imagen("chair1",(j*100+50),600-i*150-100-32-50));               
            }
        }
    }    
    
  
    
    public void atenderCliente(Barbero barber) throws InterruptedException{
        barber.direccion = 1; 
        
        clientes.acquire();
        
        sillasAccesibles.acquire();
        sillasLibres += 1;
        barberoListo.release();
        sillasAccesibles.release();
        barber.cortarCabello();
   
    }   
    
    public void llegadaCliente(Cliente customer) throws InterruptedException{
        
        sillasAccesibles.acquire();
        
        if (sillasLibres > 0) {
            
            sillasLibres -= 1;
            customer.setSillaAsignada(asignarSilla());
            Log(customer.getName() + " se le asignó la silla número " + customer.getSillaAsignada());
            customer.getCurrentAnimation().start();
            sillasAccesibles.release();
            customer.turno.acquire();
            barberoListo.acquire();
            customer.recibirCortarCabello();
            Log(customer.getName() + " se va de la barbería con un nuevo corte.");
    
        }else{
            
            sillasAccesibles.release();
            Log("No hay sillas libres. "+ customer.getName() + " se va de la barbería.");
            
        }
        
        lienzo.clientes.remove(customer);
            //System.out.println(nombre + " se va de la barbería.");
    }
    
    public synchronized int asignarSilla(){
        for (int i = 0; i < sillas.length; i++) {
            if (!sillas[i]) {
                sillas[i] = true;
                return i;
            }
        }
        return -1;
        
    }
    
    
    public static void Log(String mensaje){
        System.out.println(mensaje);
    }
    
}
