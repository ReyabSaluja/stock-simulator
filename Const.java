import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class Const {

    //  Screen
    public static final int WIDTH = 1400;
    public static final int HEIGHT = 800;
    public static final int DELAY = 10;

    //  States
    public static final String START = "START";
    public static final String PORTFOLIO = "PORTFOLIO";
    public static final String TRADE = "TRADE";
    public static final String CREATORS = "CREATORS";
    public static final String ABOUT = "ABOUT";

    //  Other Buttons
    public static final String SEARCH = "SEARCH";

    //  Fonts
    private static final Font MENU_FONT = CustomFont.loadFont("Fonts/Myriad Pro Semibold.ttf", 10);

    public static final Font MENU_FONT_XS = MENU_FONT.deriveFont(20f);
    public static final Font MENU_FONT_S = MENU_FONT.deriveFont(30f);
    public static final Font MENU_FONT_M = MENU_FONT.deriveFont(40f);
    public static final Font MENU_FONT_L = MENU_FONT.deriveFont(50f);
    public static final Font MENU_FONT_XL = MENU_FONT.deriveFont(60f);

    public static final Font MENU_FONT_XL_BOLD = MENU_FONT.deriveFont(Font.BOLD, 60f);

    public static final Font ARIAL_FONT = new Font("Arial", Font.PLAIN, 15);

    //  Colors
    public static final Color WHITE = new Color (255, 255, 255);
    public static final Color PRIMARYBLACK = new Color(18, 18, 18);
    public static final Color SECONDARYBLACK = new Color(24, 24, 24);
    public static final Color LIGHTBLUE = new Color (148,148,172);
    public static final Color DARKBLUE = new Color (51,58,86,255);
    public static final Color LIGHTGREY = new Color (161,161,161,255);
    public static final Color DARKGREY = new Color (75,75,75,255);
    
    //  Images
    public static final String GUI_START_IMAGE = "Images/StockSimulator - User Interface Start.png";
    public static final String SEARCH_ICON_IMAGE = "Images/SearchIcon.png";

    //  Graph
    public static final int PADDING = 25;
    public static final int LABEL_PADDING = 25;

    public static final Color LINE_COLOR = LIGHTBLUE;
    public static final Color POINT_COLOR = DARKBLUE;
    public static final Color GRID_COLOR = Color.WHITE;
    public static final Stroke GRAPH_STROKE = new BasicStroke(2f);
}