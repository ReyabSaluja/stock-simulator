import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class GraphicalUserInterface {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color backgroundColor;

    private GraphicalUserInterfaceItem[] guiItems;
    //----------------------------------------------------------------------------
    GraphicalUserInterface() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.backgroundColor = Color.BLACK;
    }
    GraphicalUserInterface(int x, int y, int width, int height, Color backgroundColor, GraphicalUserInterfaceItem[] guiItems) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.guiItems = guiItems;
    }
    //------------------------------------------------------------------------------   
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

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

    public GraphicalUserInterfaceItem getMenuItem(int index) {
        return this.guiItems[index];
    }
    //----------------------------------------------------------------------------
    /** 
     * Draws the menu onto a Graphics panel.
     * @param g
     */
    public void draw(Graphics g) {
        g.setColor(this.backgroundColor);
        g.fillRect(this.x, this.y, this.width, this.height);

        for (int menuItemIndex = 0; menuItemIndex < guiItems.length; menuItemIndex++) {
            guiItems[menuItemIndex].draw(g);
        }
    }
    //----------------------------------------------------------------------------
    /**
     * Resets the color to the original of all the buttons inside the menu.
     */
    public void resetButtons() {
        for (int menuItemIndex = 0; menuItemIndex < guiItems.length; menuItemIndex++) {
            if (guiItems[menuItemIndex] instanceof Button) {
                ((Button) guiItems[menuItemIndex]).resetColor();
            }
        }
    }
    //---------------------------------------------------------------------------- 
    /** 
     * Find all the buttons inside the menu that collided with the passed coordinate.
     * @param x
     * @param y
     * @return ArrayList<Integer>
     */
    public ArrayList < Integer > findCollidedButtons(int x, int y) {
        ArrayList < Integer > collidedButtons = new ArrayList < Integer > ();

        for (int menuItemIndex = 0; menuItemIndex < guiItems.length; menuItemIndex++) {
            if (guiItems[menuItemIndex] instanceof Button) {
                Button menuButton = (Button) guiItems[menuItemIndex];

                if (menuButton.inside(x, y)) {
                    collidedButtons.add(menuItemIndex);
                }
            }
        }
        return collidedButtons;
    }
}
