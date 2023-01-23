import java.awt.Graphics;
import java.awt.Color;

/**
 *  The Rect class is a template for making blank rectangles.
 *  The Rect class inherits from MenuItem.
 * 
 *  @see     DisplayItem
 *  @see     Image
 *  @author  Reyab Saluja
 *  @version "01/18/2022"
 */

public class Rect extends DisplayItem {
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

    Rect(int x, int y, Color color, int width, int height, int borderWidth, Color borderColor) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }
    //------------------------------------------------------------------------------
    //  Getters and Setters
    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBorderWidth() {
        return this.width;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
    //------------------------------------------------------------------------------ 
    /** 
     *  Draws the rect onto a Graphics panel.
     * 
     *  @param  g
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