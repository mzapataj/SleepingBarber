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
    public static void main(String[] args) {
        Barberia barberia = new Barberia(5);
        Barbero barbero = new Barbero(barberia, "Barbero");
        Cliente cliente1 = new Cliente(barberia, "Cliente 1");
        Cliente cliente2 = new Cliente(barberia, "Cliente 2");
        
        barbero.start();
        cliente1.start();
        cliente2.start();
    }
}
