import java.awt.Graphics;
import java.awt.Color;

/**
 * The Menu class is the parent of every possible menu element.
 * 
 *  @see     Menu
 *  @see     Rext
 *  @see     Text
 *  @see     Image
 *  @see     Button
 *  @author  Reyab Saluja
 *  @version 01/01/2022
 */

public abstract class DisplayItem {
    private int x;
    private int y;
    private Color color;
    //------------------------------------------------------------------------------     
    DisplayItem(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
    //------------------------------------------------------------------------------ 
    // getters
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public Color getColor() {
        return this.color;
    }
    // setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    //------------------------------------------------------------------------------ 
    /** 
     *  Every child of this class must implement their own custom draw Method.
     * 
     *  @param g
     */
    public abstract void draw(Graphics g);
}