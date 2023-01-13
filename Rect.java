import java.awt.Graphics;
import java.awt.Color;

/**
 * The Rect class is a template for making blank rectangles.
 * The Rect class inherits from MenuItem.
 * 
 * @see GraphicalUserInterfaceItem
 * @see Image
 * @author Reyab Saluja
 * @version "1.8.0_322"
 */

public class Rect extends GraphicalUserInterfaceItem {
    private int width;
    private int height;
    private int borderWidth;
    private Color borderColor;
    //------------------------------------------------------------------------------    
    Rect(int x, int y, Color color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.borderWidth = 0;
    }
    //------------------------------------------------------------------------------    
    Rect(int x, int y, Color color, int width, int height, int borderWidth, Color borderColor) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    //------------------------------------------------------------------------------
    // getters
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public int getBorderWidth() {
        return this.width;
    }
    public Color getBorderColor() {
        return this.borderColor;
    }
    // setters
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    //------------------------------------------------------------------------------ 
    /** 
     * Draws the rect onto a Graphics panel.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.fillRect(this.getX(), this.getY(), this.width, this.height);

        if (this.borderWidth > 0) {
            g.setColor(this.borderColor);
            g.fillRect(this.getX() - this.borderWidth, this.getY() - this.borderWidth, this.width + this.borderWidth, this.height + this.borderWidth);
        }
    }
}