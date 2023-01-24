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
    public static final String LOGIN = "LOG IN";
    public static final String SUBMIT = "SUBMIT";

    //  Displays
    public static final int PORTFOLIO_BUTTON_X = 50;
    public static final int TRADE_BUTTON_X = 225;
    public static final int CREATORS_BUTTON_X = 330;
    public static final int ABOUT_BUTTON_X = 525;

    public static final int DISPLAY_BUTTONS_WIDTH = 100;
    public static final int DISPLAY_BUTTONS_HEIGHT = 50;

    public static final int DISPLAY_BUTTONS_Y = 100;

    public static final int LOGIN_TITLE_PART1_X = 570;
    public static final int LOGIN_TITLE_PART2_X = 665;

    public static final int LOGIN_TITLE_Y = 130;

    //  Fonts
    private static final Font MYRIAD = CustomFont.loadFont("Fonts/Myriad Pro Semibold.ttf", 10);
    public static final Font MYRIAD_XS = MYRIAD.deriveFont(20f);
    public static final Font MYRIAD_S = MYRIAD.deriveFont(30f);
    public static final Font MYRIAD_M = MYRIAD.deriveFont(40f);
    public static final Font MYRIAD_L = MYRIAD.deriveFont(50f);
    public static final Font MYRIAD_XL = MYRIAD.deriveFont(60f);

    public static final Font ARIAL = new Font("Arial", Font.PLAIN, 15);

    private static final Font OPENSANS = CustomFont.loadFont("Fonts/OpenSans-Regular.ttf", 10);
    public static final Font OPENSANS_XXS = OPENSANS.deriveFont(15f);
    public static final Font OPENSANS_XS = OPENSANS.deriveFont(20f);
    public static final Font OPENSANS_S = OPENSANS.deriveFont(25f);
    public static final Font OPENSANS_M = OPENSANS.deriveFont(40f);
    public static final Font OPENSANS_L = OPENSANS.deriveFont(50f);
    public static final Font OPENSANS_XL = OPENSANS.deriveFont(60f);

    private static final Font OPENSANSBOLD = CustomFont.loadFont("Fonts/OpenSans-Semibold.ttf", 10);
    public static final Font OPENSANS_BOLD_XS = OPENSANSBOLD.deriveFont(12f);
    public static final Font OPENSANS_BOLD_S = OPENSANSBOLD.deriveFont(25f);
    public static final Font OPENSANS_BOLD_M = OPENSANSBOLD.deriveFont(35f);
    public static final Font OPENSANS_BOLD_L = OPENSANSBOLD.deriveFont(50f);

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