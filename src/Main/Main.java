package Main;


import Graficos.Lienzo;
import Logic.Cliente;
import Logic.Barbero;
import Logic.Barberia;
import java.awt.Color;
import javax.swing.JFrame;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mzapataj
 */

public class Main     {
    public static void main(String[] args) throws InterruptedException {
        
        InterfazGrafica ui = new InterfazGrafica();
        Lienzo lienzo = new Lienzo();
        lienzo.setSize(500, 500);
        lienzo.setBackground(Color.GREEN);
        ui.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        ui.add(lienzo);
        ui.setVisible(true);
        lienzo.start();
        
        /*Barberia barberia = new Barberia(10);
        Barbero barbero = new Barbero(barberia, "Barbero");
        barbero.start();
        
        for (int i = 1; i <= 10; i++) {
            Cliente cliente = new Cliente(barberia, "Cliente "+i);
            cliente.start();
            Thread.sleep(2000);
        }*/
        /*
        Cliente cliente1 = new Cliente(barberia, "Cliente 1");
        Cliente cliente2 = new Cliente(barberia, "Cliente 2");
        Cliente cliente3 = new Cliente(barberia, "Cliente 3");
        Cliente cliente4 = new Cliente(barberia, "Cliente 4");
        Cliente cliente5 = new Cliente(barberia, "Cliente 5");
        Cliente cliente6 = new Cliente(barberia, "Cliente 6");
        Cliente cliente7 = new Cliente(barberia, "Cliente 7");
        Cliente cliente8 = new Cliente(barberia, "Cliente 8");
        Cliente cliente9 = new Cliente(barberia, "Cliente 9");
        Cliente cliente10 = new Cliente(barberia, "Cliente 10");
        
        barbero.start();
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();
        cliente6.start();
        cliente7.start();
        cliente8.start();
        cliente9.start();
        cliente10.start();
    */
    }
}
