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

    public static final int STOCK_X = 50;
    public static final int STOCK_PART2_X = 185;
    public static final int STOCK_Y = 15;

    public static final int DISPLAY_BUTTONS_WIDTH = 100;
    public static final int DISPLAY_BUTTONS_HEIGHT = 50;

    public static final int DISPLAY_BUTTONS_Y = 100;

    public static final int LOGIN_TITLE_PART1_X = 570;
    public static final int LOGIN_TITLE_PART2_X = 665;

    public static final int LOGIN_TITLE_Y = 130;

    //  Sign In
    public static final int LOGIN_BUTTON_BACKGROUND_X = 630;
    public static final int LOGIN_BUTTON_BACKGROUND_Y = 450;

    public static final int LOGIN_TEXT_X = 660;
    public static final int LOGIN_TEXT_Y = 450;

    public static final int SIGN_IN_TEXT_X = Const.WIDTH / 2 - 250;
    public static final int SIGN_IN_TEXT_Y = 200;

    //  Search Field
    public static final int SEARCH_FIELD_BOUNDS_X = 50;
    public static final int SEARCH_FIELD_BOUNDS_Y = 275;
    public static final int SEARCH_FIELD_BOUNDS_WIDTH = 500;
    public static final int SEARCH_FIELD_BOUNDS_HEIGHT = 70;

    //  Login Screen
    public static final int USERNAME_BOUNDS_Y = 250;
    public static final int PASSWORD_BOUNDS_Y = 350;

    public static final int USERNAME_PASSWORD_BOUNDS_X = Const.WIDTH / 2 - 250;
    public static final int USERNAME_PASSWORD_BOUNDS_WIDTH = 500;
    public static final int USERNAME_PASSWORD_BOUNDS_HEIGHT = 70;

    //  Action Field
    public static final int ACTION_FIELD_BOUNDS_X = 50;
    public static final int ACTION_FIELD_BOUNDS_Y = 385;
    public static final int ACTION_FIELD_BOUNDS_WIDTH = 200;
    public static final int ACTION_FIELD_BOUNDS_HEIGHT = 50;

    //  Holdings Field
    public static final int HOLDINGS_X = 80;
    public static final int HOLDINGS_Y = 500;
    public static final int HOLDINGS_WIDTH = Const.WIDTH - 200;
    public static final int HOLDINGS_HEIGHT = 200;

    //  Account Value Field
    public static final int ACCOUNT_VALUE_X = 70;
    public static final int ACCOUNT_VALUE_Y = 325;
    public static final int ACCOUNT_VALUE_WIDTH = 175;
    public static final int ACCOUNT_VALUE_HEIGHT = 75;

    //  About Field 
    public static final int ABOUT_FIELD_X = 220;
    public static final int ABOUT_FIELD_Y = 325;
    public static final int ABOUT_FIELD_WIDTH = Const.WIDTH - 200;
    public static final int ABOUT_FIELD_HEIGHT = 700;

    //  Creators Field
    public static final int CREATORS_FIELD_X = 250;
    public static final int CREATORS_FIELD_Y = 325;
    public static final int CREATORS_FIELD_WIDTH = Const.WIDTH - 200;
    public static final int CREATORS_FIELD_HEIGHT = 700;

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