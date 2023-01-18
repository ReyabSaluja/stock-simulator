import java.awt.Graphics;
import java.awt.Color;

/**
 * The Button class is a template for making buttons. It works with the main program to add functionality. 
 * The Button class inherits from MenuItem. 
 * 
 * @see Rect
 * @see Text
 * @see GraphicalUserInterfaceItem
 * @author Reyab Saluja
 * @version "1.8.0_322"
 */

public class Button extends GraphicalUserInterfaceItem {
    private Rect rect;
    private Text text;
    private int buttonEventType;
    private String buttonFunction;
    private Color originalColor;
    private Color hoveredColor;
    //------------------------------------------------------------------------------    
    Button(Rect rect, Text text, int buttonEventType, String buttonFunction, Color hoveredColor, boolean inheritSize, boolean isCentered) {
        super(rect.getX(), rect.getY(), rect.getColor());
        this.rect = rect;
        this.text = text;

        this.originalColor = this.text.getColor();
        this.hoveredColor = hoveredColor;

        this.buttonEventType = buttonEventType;
        this.buttonFunction = buttonFunction;

        if (inheritSize) {
            this.rect.setWidth(this.text.getWidth());
            this.rect.setHeight(this.text.getHeight());
        }

        if (isCentered) {
            this.text.centerText(this.rect);
        } else {
            this.text.setX(this.rect.getX());
            this.text.setY(this.rect.getY());
        }
    }
    //------------------------------------------------------------------------------  
    public String getButtonFunction() {
        return this.buttonFunction;
    }
    //------------------------------------------------------------------------------  
    public void resetColor() {
        this.text.setColor(originalColor);
    }

    public void setTextColor(Color newColor) {
        this.text.setColor(newColor);
    }
    //------------------------------------------------------------------------------  
    public void hoveredColor() {
        this.text.setColor(hoveredColor);
    }

    /** 
     * Draws the button onto a Graphics panel.
     * 
     *  @param g
     */
    public void draw(Graphics g) {
        this.rect.draw(g);
        this.text.draw(g);
    }

    /** 
     * Centers the middle of the button to the passed coordinate.
     * 
     *  @param x
     *  @param y
     */
    public void centerButton(int x, int y) {
        this.setX((int)(x - this.rect.getWidth() / 2));
        this.setY((int)(y - this.rect.getWidth() / 2));

        this.rect.setX(this.getX());
        this.rect.setY(this.getY());

        this.text.centerText(this.rect);
    }

    /** 
     * Returns true if the passed coordinate collides with the button. 
     * 
     *  @param x
     *  @param y
     *  @return boolean
     */
    public boolean inside(int x, int y) {
        return (x > this.getX() && x < this.getX() + this.rect.getWidth() && y > this.getY() && y < this.getY() + this.rect.getHeight());
    }

    /** 
     * Check if the passed mouseEvent type is the button's mouseEvent type. 
     * 
     *  @param mouseEventType
     *  @return boolean
     */
    public boolean checkButtonType(int mouseEventType) {
        return (this.buttonEventType == mouseEventType);
    }
}