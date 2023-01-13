import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.FontUIResource;

public class Const {

    //  Fonts
    private static final Font MENU_FONT = CustomFont.loadFont("Fonts/Myriad Pro Semibold.ttf", 10);

    public static final Font MENU_FONT_XS = MENU_FONT.deriveFont(20f);
    public static final Font MENU_FONT_S = MENU_FONT.deriveFont(30f);
    public static final Font MENU_FONT_M = MENU_FONT.deriveFont(40f);
    public static final Font MENU_FONT_L = MENU_FONT.deriveFont(50f);
    public static final Font MENU_FONT_XL = MENU_FONT.deriveFont(60f);

    public static final Font MENU_FONT_XL_BOLD = MENU_FONT.deriveFont(Font.BOLD, 60f);

    //  Colors
    public static final Color RED = Color.RED;
    public static final Color BLUE = new Color (7, 132, 181);
    public static final Color WHITE = new Color (255, 255, 255);
    public static final Color PRIMARYBLACK = new Color(18, 18, 18);
    public static final Color SECONDARYBLACK = new Color(24, 24, 24);
    
    //  Images
    public static final String GUI_START_IMAGE = "Images/StockSimulator - User Interface Start.png";
}