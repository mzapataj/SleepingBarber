package Graficos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagen {

    private BufferedImage sprite;
    public String name;
    public static final int TOTAL_SPRITES = 15;
    public int x;
    public int y;
    
    public Imagen(String file) {
        name = file;
         try {
            sprite = ImageIO.read(new File("res/" + name + ".png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Imagen(String file, int x, int y) {
        name = file;
         try {
            sprite = ImageIO.read(new File("res/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
        
    }
    
    public BufferedImage getImagen() {
        return sprite;
    }
    
    public void drawImagen(Graphics g){
        g.drawImage(getImagen(), x, y, null);
    }

}
