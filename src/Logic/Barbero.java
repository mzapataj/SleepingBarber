package Logic;

import Graficos.Animation;
import Graficos.Imagen;
import Graficos.Lienzo;
import static Logic.Barberia.Log;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mzapataj
 */
public class Barbero extends Persona {

    boolean estaDormido;
    public static boolean haSidoPausado;    
        
    public Barbero(Barberia b, String nombreProceso, ResourceBundle resourceBundle) {

        super(b, nombreProceso, resourceBundle);
        estaDormido = true;        
        cargarAnimaciones("barber", 0);
        BufferedImage[] up = {new Imagen("barberUp2").getImagen(), new Imagen("barberUp3").getImagen()};
        animaciones[0] = new Animation(up, 10, 0, -1*0);
        animaciones[0].setLocation(660, 155);
        animaciones[1].setLocation(660, 155);
        animaciones[2].setLocation(660, 155);
        animaciones[3].setLocation(660, 155);

    }

    public boolean estaDormido() {
        return estaDormido;
    }

    public void setEstaDormido(boolean estaDormido) {
        this.estaDormido = estaDormido;
    }

    public void cortarCabello() {
        setCurrentAnimation(getAnimaciones()[0]);
        getCurrentAnimation().start();
        Barberia.Log(
                String.format(messages.getString("cut-hair"),getName() ) 
            );
        long delta = 0;
        

        try {

            sleep(5000);
            setCurrentAnimation(getAnimaciones()[1]);

        } catch (InterruptedException ex) {

        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                barberia.atenderCliente(this);
            } catch (InterruptedException ex) {
                
            }
        }
    }

    @Override
    public void moveHandler(Lienzo lienzo) {

        if (!lienzo.clientes.isEmpty()) {
            if (estaDormido) {                
                Log(String.format( messages.getString("barber-wake"), getName()) );                
                setCurrentAnimation(getAnimaciones()[direccion]);
                getCurrentAnimation().start();
                setEstaDormido(false);
            }
        } else {
            if (!estaDormido) {                
                Log(String.format( messages.getString("barber-sleep"), getName()) );                    
                setCurrentAnimation(getAnimaciones()[direccion]);
                getCurrentAnimation().stop();
                setEstaDormido(true);
            }
        }

    }
}
