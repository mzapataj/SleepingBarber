package Graficos;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage sprite;
    public String name;
    public static final int TOTAL_SPRITES = 15;

    public Sprite(String file) {
        name = file;
         try {
            sprite = ImageIO.read(new File("res/" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite() {
        return sprite;
    }

}
