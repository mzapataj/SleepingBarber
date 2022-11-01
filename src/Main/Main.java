package Main;

import Graficos.Lienzo;
import Logic.Barberia;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mzapataj
 */
public class Main {

    public static Barberia barberia;
    public static long tiempoInicioPausa;
    public static long tiempoFinalPausa;
    
    public static void main(String[] args) throws InterruptedException {

        Locale locale = Locale.getDefault();            
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);


        int numSillas;
        do{
            try {
                numSillas = Integer.valueOf(JOptionPane.showInputDialog(null,  messages.getString("chairs-number"), messages.getString("sleeping-barber"), 1));
            } catch (NumberFormatException e) {
                numSillas = -1;
            }
        }while(numSillas > 12);
        
        if (numSillas != -1) {
            
            InterfazGrafica ui = new InterfazGrafica(messages);
            ui.setExtendedState(JFrame.MAXIMIZED_BOTH);
            ui.setVisible(true);
            
            

            barberia = new Barberia(numSillas,messages);

            Lienzo lienzo = new Lienzo(ui.getWidth(), ui.getHeight(), barberia, messages);
            barberia.setLienzo(lienzo);
            lienzo.barber.start();
            
            ui.inicializarBotonCliente();
            ui.inicializarBotonPausa();
            ui.inicializarBotonClienteIdos();
            
            ui.setLienzo(lienzo);
            ui.add(lienzo);

            lienzo.start();
        }
    }
}
