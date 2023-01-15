import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

    
 

    private JFrame window;
    private JPanel panel;
    private MenuMouseListener menuMouseListener;
    private MenuMotionListener menuMotionListener;
     

    private String state = START;
    //----------------------------------------------------------------------------
    public YahooFinanceScraper() {
        //  Window Initialization
        this.window = new JFrame("Stock Simulator");
        this.window.setSize(WIDTH, HEIGHT);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        //  Start Panel Initialization
        this.panel = new GraphicsPanel();
		this.panel.setBackground(Color.BLACK);
		this.window.add(this.panel);
        //  Creating Mouse Listener
        menuMouseListener = new MenuMouseListener();
        this.panel.addMouseListener(menuMouseListener);
        //  Creating Mouse Motion Listener
        menuMotionListener = new MenuMotionListener();
        this.panel.addMouseMotionListener(menuMotionListener);
        //Setting up graph
        LineChart chart = new LineChart(new Stock("VOO"));
        chart.setPreferredSize(new Dimension(1400 ,800));
        window.add(chart);
        window.pack();

    
        //Setting up graph
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
    public class GraphicsPanel extends JPanel {
        GraphicsPanel() {
            this.setFocusable(true);
            this.requestFocusInWindow();
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //Draws a rectangle with the text "EXIT" on it

            //  Start State
            if (state.equalsIgnoreCase(START)) {
                startUI.draw(g);
            } else if (state.equalsIgnoreCase(PORTFOLIO)) {
                portfolioUI.draw(g);
                ((Button) portfolioItems[portfolioButtonIndex]).setColor(Const.BLUE);
            } else if (state.equalsIgnoreCase(TRADE)) {
                tradeUI.draw(g);
                ((Button) tradeItems[tradeButtonIndex]).setColor(Const.BLUE);
            } else if (state.equalsIgnoreCase(CREATORS)) {
                creatorsUI.draw(g);
                ((Button) creatorsItems[creatorsButtonIndex]).setColor(Const.BLUE);
            } else if (state.equalsIgnoreCase(ABOUT)) {
                aboutUI.draw(g);
                ((Button) aboutItems[aboutButtonIndex]).setColor(Const.BLUE);
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
            portfolioUI.resetButtons();

            ArrayList < Integer > hoveredButtons = portfolioUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) portfolioUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(TRADE)) {
            tradeUI.resetButtons();

            ArrayList < Integer > hoveredButtons = tradeUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) tradeUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(CREATORS)) {
            creatorsUI.resetButtons();

            ArrayList < Integer > hoveredButtons = creatorsUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) creatorsUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(ABOUT)) {
            aboutUI.resetButtons();

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
        } else if (buttonFunction.equalsIgnoreCase(PORTFOLIO)) {
            state = PORTFOLIO;
        } else if (buttonFunction.equalsIgnoreCase(TRADE)) {
            state = TRADE;
        } else if (buttonFunction.equalsIgnoreCase(CREATORS)) {
            state = CREATORS;
        } else if (buttonFunction.equalsIgnoreCase(ABOUT)) {
            state = ABOUT;
        } 
    }
    //----------------------------------------------------------------------------
    //  Graphical User Interfaces
	GraphicalUserInterfaceItem[] startItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.PRIMARYBLACK, 100, 50), new Text(50, 100, "PORTFOLIO", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.PRIMARYBLACK, 100, 50), new Text(250, 100, "TRADE", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.PRIMARYBLACK, 100, 50), new Text(400, 100, "CREATORS", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.PRIMARYBLACK, 100, 50), new Text(600, 100, "ABOUT", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true)
    };
	
	GraphicalUserInterface startUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Color.BLACK, startItems);

    GraphicalUserInterfaceItem[] portfolioItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.PRIMARYBLACK, 100, 50), new Text(50, 100, "PORTFOLIO", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.PRIMARYBLACK, 100, 50), new Text(250, 100, "TRADE", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.PRIMARYBLACK, 100, 50), new Text(400, 100, "CREATORS", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.PRIMARYBLACK, 100, 50), new Text(600, 100, "ABOUT", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true)
    };

    int portfolioButtonIndex = 3;

    GraphicalUserInterface portfolioUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Color.BLACK, portfolioItems);

    GraphicalUserInterfaceItem[] tradeItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.PRIMARYBLACK, 100, 50), new Text(50, 100, "PORTFOLIO", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.PRIMARYBLACK, 100, 50), new Text(250, 100, "TRADE", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.PRIMARYBLACK, 100, 50), new Text(400, 100, "CREATORS", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.PRIMARYBLACK, 100, 50), new Text(600, 100, "ABOUT", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true),
    };

    int tradeButtonIndex = 4;

    GraphicalUserInterface tradeUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Color.BLACK, tradeItems);

    GraphicalUserInterfaceItem[] creatorsItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.PRIMARYBLACK, 100, 50), new Text(50, 100, "PORTFOLIO", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.PRIMARYBLACK, 100, 50), new Text(250, 100, "TRADE", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.PRIMARYBLACK, 100, 50), new Text(400, 100, "CREATORS", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.PRIMARYBLACK, 100, 50), new Text(600, 100, "ABOUT", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true)
    };

    int creatorsButtonIndex = 5;

    GraphicalUserInterface creatorsUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Color.BLACK, creatorsItems);

    GraphicalUserInterfaceItem[] aboutItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.BLUE, true),
        new Button(new Rect(50, 100, Const.PRIMARYBLACK, 100, 50), new Text(50, 100, "PORTFOLIO", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "PORTFOLIO", Const.BLUE, true, true),
        new Button(new Rect(250, 100, Const.PRIMARYBLACK, 100, 50), new Text(250, 100, "TRADE", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "TRADE", Const.BLUE, true, true),
        new Button(new Rect(400, 100, Const.PRIMARYBLACK, 100, 50), new Text(400, 100, "CREATORS", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "CREATORS", Const.BLUE, true, true),
        new Button(new Rect(600, 100, Const.PRIMARYBLACK, 100, 50), new Text(600, 100, "ABOUT", Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, "ABOUT", Const.BLUE, true, true)
    };

    int aboutButtonIndex = 6;

    GraphicalUserInterface aboutUI = new GraphicalUserInterface(0, 0, WIDTH, HEIGHT, Color.BLACK, aboutItems);
    //----------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {

        YahooFinanceScraper test = new YahooFinanceScraper();
        test.run();

    }
}