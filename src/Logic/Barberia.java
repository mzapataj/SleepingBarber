package Logic;


import Graficos.Imagen;
import Graficos.Lienzo;
import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
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
    private ResourceBundle messages;
    
    public Barberia(int tam, ResourceBundle messages) {
        
        sillasLibres = tam;
        
        barberoListo = new Semaphore(0, true);
        sillasAccesibles =  new Semaphore(1, true);
        clientes = new Semaphore(0, true);
        spriteSillas = new CopyOnWriteArrayList<>();
        generateSeatsSprite();
        
        Log(messages.getString("welcome"));
        sillas = new boolean[tam];
        
        this.messages=messages;
        
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
            
            Log(String.format(messages.getString("assign-chair"), customer.getName(), String.valueOf(customer.getSillaAsignada()+1)));
            customer.getCurrentAnimation().start();
            sillasAccesibles.release();
            customer.turno.acquire();
            barberoListo.acquire();
            customer.recibirCortarCabello();
            Log(String.format(messages.getString("exit-barbery"), customer.getName()) );
    
        }else{
            
            sillasAccesibles.release();
            Log( String.format(messages.getString("not-available-chairs") , customer.getName()) );
            Cliente.clientesIdos++;
            
            customer.removerLabel();
        }
        
        lienzo.clientes.remove(customer);            
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
