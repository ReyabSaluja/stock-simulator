import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class YahooFinanceScraper {
    static final int WIDTH = 1400;
    static final int HEIGHT = 800;
    static final int DELAY = 10;

    static final String START = "START";
    static final String PORTFOLIO = "PORTFOLIO";
    static final String TRADE = "TRADE";
    static final String CREATORS = "CREATORS";
    static final String ABOUT = "ABOUT";

    JTextField searchField;
    LineChart chart;

    GraphicalUserInterface startUI, portfolioUI, tradeUI, creatorsUI, aboutUI;

    GridBagLayout gbl;
    GridBagConstraints gbc;

    private JFrame window;
    private MenuMouseListener mouseListener;
    private MenuMotionListener mouseMotionListener;

    private String state = START;
    //----------------------------------------------------------------------------
    public YahooFinanceScraper() {
        //  Window Initialization
        this.window = new JFrame("Stock Simulator");
        this.window.setSize(WIDTH, HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.setLocationRelativeTo(null);
        //  Mouse and Mouse Motion Initialization
        mouseListener = new MenuMouseListener();
        mouseMotionListener = new MenuMotionListener();
        this.window.addMouseListener(mouseListener);
        this.window.addMouseMotionListener(mouseMotionListener);
        //  Panels Initialization
        this.startUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Const.PRIMARYBLACK, startItems);
        this.portfolioUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Const.PRIMARYBLACK, portfolioItems);
        this.tradeUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Const.PRIMARYBLACK, tradeItems);
        this.creatorsUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Const.PRIMARYBLACK, creatorsItems);
        this.aboutUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Const.PRIMARYBLACK, aboutItems);
        //  Search Field Initialization
        this.searchField = new JTextField("Enter Ticker...");
        Dimension searchFieldDimensions = searchField.getPreferredSize();
        this.searchField.setBounds(150, 200, searchFieldDimensions.width, searchFieldDimensions.height);
        //  Adding Search Field to Trade UI
        this.tradeUI.setLayout(null);
        this.tradeUI.add(searchField);
        //  Graph Initialization
        this.chart = new LineChart(new Stock("NKE"));
        this.chart.setPreferredSize(new Dimension(600, 400));
        this.chart.setBounds(300, 300, 600, 400);
        //  Adding Chart to Trade UI
        this.tradeUI.setLayout(null);
        this.tradeUI.add(chart);
        //  Window
        this.window.setContentPane(startUI);
        this.window.setVisible(true);
    }
    //----------------------------------------------------------------------------
    public void run() {
        //	Main Game Loop
        while (true) {
            try {
                window.repaint();
                Thread.sleep(15);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    //----------------------------------------------------------------------------
    public class MenuMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) { // Listens to clicks
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_CLICKED);
        }
        public void mousePressed(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_PRESSED);
        }
        public void mouseReleased(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_RELEASED);
        }
        public void mouseEntered(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_ENTERED);
        }
        public void mouseExited(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            callRanButtons(mouseX, mouseY, MouseEvent.MOUSE_EXITED);
        }
    }
    //----------------------------------------------------------------------------
    public class MenuMotionListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) { // MUST be implemented even if not used!
        }
        public void mouseMoved(MouseEvent e) { // MUST be implemented even if not used!
            int mouseX = e.getX();
            int mouseY = e.getY();

            hoverButtons(mouseX, mouseY);
        }
    }
    //----------------------------------------------------------------------------
    /** 
     *   Check if any button in the current game state menu is colliding with the mouse.
     *   If so, change the button to its hover color, otherwise, reset the button to the original color.
     *
     *   @param mouseX
     *   @param mouseY
     */
    public void hoverButtons(int mouseX, int mouseY) {
        if (state.equalsIgnoreCase(START)) {
            startUI.resetButtons();

            ArrayList < Integer > hoveredButtons = startUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) startUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(PORTFOLIO)) {
            portfolioUI.resetButtonsExcept(3);

            ArrayList < Integer > hoveredButtons = portfolioUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) portfolioUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(TRADE)) {
            tradeUI.resetButtonsExcept(4);

            ArrayList < Integer > hoveredButtons = tradeUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) tradeUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(CREATORS)) {
            creatorsUI.resetButtonsExcept(5);

            ArrayList < Integer > hoveredButtons = creatorsUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) creatorsUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(ABOUT)) {
            aboutUI.resetButtonsExcept(6);

            ArrayList < Integer > hoveredButtons = aboutUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) aboutUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        }
    }
    //----------------------------------------------------------------------------
    /** 
     *  Check if any of the button's in the current game state has been collided with and activated.
     *  If so, run their custom button function. 
     * 
     *	@param mouseX
     *  @param mouseY
     *  @param mouseEvent
     */
    public void callRanButtons(int mouseX, int mouseY, int mouseEvent) {
        if (state.equalsIgnoreCase(START)) {
            ArrayList < Integer > ranButtons = startUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) startUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(PORTFOLIO)) {
            ArrayList < Integer > ranButtons = portfolioUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) portfolioUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(TRADE)) {
            ArrayList < Integer > ranButtons = tradeUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) tradeUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(CREATORS)) {
            ArrayList < Integer > ranButtons = creatorsUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) creatorsUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(ABOUT)) {
            ArrayList < Integer > ranButtons = aboutUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) aboutUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        }
    }
    //----------------------------------------------------------------------------
    /** 
     *  Contains the functionality of every custom button function.
     * 
     *  @param buttonFunction
     */
    public void runButtonFunction(String buttonFunction) {
        if (buttonFunction.equalsIgnoreCase(START)) {
            state = START;
            this.window.setContentPane(startUI);
        } else if (buttonFunction.equalsIgnoreCase(PORTFOLIO)) {
            state = PORTFOLIO;
            this.window.setContentPane(portfolioUI);
            ((Button) portfolioItems[3]).setTextColor(Const.BLUE);
        } else if (buttonFunction.equalsIgnoreCase(TRADE)) {
            state = TRADE;
            this.window.setContentPane(tradeUI);
            ((Button) tradeItems[4]).setTextColor(Const.BLUE);
        } else if (buttonFunction.equalsIgnoreCase(CREATORS)) {
            state = CREATORS;
            this.window.setContentPane(creatorsUI);
            ((Button) creatorsItems[5]).setTextColor(Const.BLUE);
        } else if (buttonFunction.equalsIgnoreCase(ABOUT)) {
            state = ABOUT;
            this.window.setContentPane(aboutUI);
            ((Button) aboutItems[6]).setTextColor(Const.BLUE);
        }
    }
    //----------------------------------------------------------------------------
    //  Graphical User Interfaces
    GraphicalUserInterfaceItem[] startItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.SECONDARYBLACK, 100, 50), new Text(50, 100, PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, PORTFOLIO, Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.SECONDARYBLACK, 100, 50), new Text(250, 100, TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, TRADE, Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.SECONDARYBLACK, 100, 50), new Text(400, 100, CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, CREATORS, Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.SECONDARYBLACK, 100, 50), new Text(600, 100, ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, ABOUT, Const.BLUE, true, true)
    };

    GraphicalUserInterfaceItem[] portfolioItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.SECONDARYBLACK, 100, 50), new Text(50, 100, PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, PORTFOLIO, Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.SECONDARYBLACK, 100, 50), new Text(250, 100, TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, TRADE, Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.SECONDARYBLACK, 100, 50), new Text(400, 100, CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, CREATORS, Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.SECONDARYBLACK, 100, 50), new Text(600, 100, ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, ABOUT, Const.BLUE, true, true)
    };

    GraphicalUserInterfaceItem[] tradeItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.SECONDARYBLACK, 100, 50), new Text(50, 100, PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.SECONDARYBLACK, 100, 50), new Text(250, 100, TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.SECONDARYBLACK, 100, 50), new Text(400, 100, CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.SECONDARYBLACK, 100, 50), new Text(600, 100, ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true),
    };

    GraphicalUserInterfaceItem[] creatorsItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.SECONDARYBLACK, 100, 50), new Text(50, 100, PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, PORTFOLIO, Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.SECONDARYBLACK, 100, 50), new Text(250, 100, TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, TRADE, Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.SECONDARYBLACK, 100, 50), new Text(400, 100, CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, CREATORS, Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.SECONDARYBLACK, 100, 50), new Text(600, 100, ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, ABOUT, Const.BLUE, true, true)
    };

    GraphicalUserInterfaceItem[] aboutItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.SECONDARYBLACK, 100, 50), new Text(50, 100, PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, PORTFOLIO, Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.SECONDARYBLACK, 100, 50), new Text(250, 100, TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, TRADE, Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.SECONDARYBLACK, 100, 50), new Text(400, 100, CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, CREATORS, Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.SECONDARYBLACK, 100, 50), new Text(600, 100, ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, ABOUT, Const.BLUE, true, true)
    };
    //----------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {

        YahooFinanceScraper test = new YahooFinanceScraper();
        test.run();

    }
}