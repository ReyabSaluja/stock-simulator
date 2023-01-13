import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * The Text class is a template for making text with custom fonts.
 * The Text class inherits from MenuItem.
 * 
 * @see GraphicalUserInterfaceItem
 * @see Image
 * @see CustomFont
 * @author Reyab Saluja
 * @version "1.8.0_322"
 */

public class Text extends GraphicalUserInterfaceItem {
    private String text;
    private Font font;
    private int width;
    private int height;
    private boolean drawn;
    private FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);

    //------------------------------------------------------------------------------  
    Text(int x, int y, String text, Font font, Color color, boolean drawn) {
        super(x, y, color);
        this.text = text;
        this.font = font;
        this.drawn = drawn;

        // Calculate the width and height of the text with the current Font.
        this.width = (int)(this.font.getStringBounds(this.text, this.frc).getWidth());
        this.height = (int)(this.font.getStringBounds(this.text, this.frc).getHeight());
    }
    //------------------------------------------------------------------------------ 
    // Getters
    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public String getText() {
        return this.text;
    }
    public Font getFont() {
        return this.font;
    }
    // Setters
    public void setText(String text) {
        this.text = text;

        // Recalculates the width and height with the new text.
        this.width = (int)(this.font.getStringBounds(this.text, this.frc).getWidth());
        this.height = (int)(this.font.getStringBounds(this.text, this.frc).getHeight());
    }
    public void setFont(Font font) {
        this.font = font;
    }
    //------------------------------------------------------------------------------  

    /** 
     * Centers the middle of the text to the passed coordinate.
     * 
     * @param x
     * @param y
     */
    public void centerText(int x, int y) {
        this.setX((int)(x - this.width / 2));
        this.setY((int)(y - this.height / 2));
    }
    
    /** 
     * Centers the middle of the text to the middle of the passed rect.
     * 
     * @param rect
     */
    public void centerText(Rect rect) {
        this.setX((int)(rect.getX() + rect.getWidth() / 2 - this.width / 2));
        this.setY((int)(rect.getY() + rect.getHeight() / 2 - this.height / 2));
    }

    //------------------------------------------------------------------------------  
    /** 
     * Draws the rect onto a Graphics panel.
     * 
     * @param g
     */
    public void draw(Graphics g) {
        if (this.drawn) {
            g.setColor(this.getColor());
            g.setFont(this.font);
            g.drawString(this.text, this.getX(), this.getY() + this.height);
        } else {
            g.drawString("", 0, 0);
        }
    }
}