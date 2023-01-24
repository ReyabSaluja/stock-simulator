import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *  The Image class is a template for making images.
 *  The Image class inherits from Rect(still a child of MenuItem).
 * 
 *  @see     MenuItem
 *  @author  Reyab Saluja
 *  @version 01/01/2022
 */

public class Image extends Rect {
    private BufferedImage image;
    //------------------------------------------------------------------------------    
    Image(int x, int y, String imagePath) {
        super(x, y, Color.BLACK, 0, 0);

        try {
            this.image = ImageIO.read(new File(imagePath));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
    }
    //------------------------------------------------------------------------------  
    /** 
     *  Resets the status and color of this tile.
     * 
     *  @param g
     */
    public void draw(Graphics g) {
        g.drawImage(this.image, this.getX(), this.getY(), null);
    }
}