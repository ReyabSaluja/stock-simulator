import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;

public class StockMarketSimulator {

    private JTextField searchField;
    private String search;

    private LineChart chart;

    private GraphicalUserInterface startUI, portfolioUI, tradeUI, creatorsUI, aboutUI;
    private JFrame window;
    private MenuMouseListener mouseListener;
    private MenuMotionListener mouseMotionListener;

    private String state = Const.START;
    //----------------------------------------------------------------------------
    public StockMarketSimulator() {
        //  Window Initialization
        window = new JFrame("Stock Simulator");
        window.setSize(Const.WIDTH, Const.HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        //  Mouse and Mouse Motion Initialization
        mouseListener = new MenuMouseListener();
        mouseMotionListener = new MenuMotionListener();
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseMotionListener);
        //  Panels Initialization
        startUI = new GraphicalUserInterface(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, startItems);
        portfolioUI = new GraphicalUserInterface(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, portfolioItems);
        tradeUI = new GraphicalUserInterface(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, tradeItems);
        creatorsUI = new GraphicalUserInterface(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, creatorsItems);
        aboutUI = new GraphicalUserInterface(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, aboutItems);
        //  Search Field Initialization
        searchField = new JTextField("", 15);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchField.setText("   Look up Company Symbol");
            }

            @Override
            public void focusLost(FocusEvent e) {
                searchField.setText("Focus Lost");
            }
        });
        //  Adding Search Field to Trade UI
        tradeUI.setLayout(null);
        searchField.setBounds(50, 275, 500, 70);
        searchField.setForeground(Const.LIGHTGREY);
        searchField.setBorder(new LineBorder(Const.LIGHTBLUE, 3, true));
        tradeUI.add(searchField);
        //  Window
        window.setContentPane(startUI);
        window.setVisible(true);
    }
    //----------------------------------------------------------------------------
    public void run() {
        //	Main Game Loop
        while (true) {
            // System.out.println(searchField.getText());
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
        if (state.equalsIgnoreCase(Const.START)) {
            startUI.resetButtons();

            ArrayList < Integer > hoveredButtons = startUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) startUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(Const.PORTFOLIO)) {
            portfolioUI.resetButtonsExcept(3);

            ArrayList < Integer > hoveredButtons = portfolioUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) portfolioUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(Const.TRADE)) {
            tradeUI.resetButtonsExcept(4);

            ArrayList < Integer > hoveredButtons = tradeUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) tradeUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(Const.CREATORS)) {
            creatorsUI.resetButtonsExcept(5);

            ArrayList < Integer > hoveredButtons = creatorsUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) creatorsUI.getMenuItem(hoveredButtonIndex));

                hoveredButton.hoveredColor();
            }
        } else if (state.equalsIgnoreCase(Const.ABOUT)) {
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
        if (state.equalsIgnoreCase(Const.START)) {
            ArrayList < Integer > ranButtons = startUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) startUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(Const.PORTFOLIO)) {
            ArrayList < Integer > ranButtons = portfolioUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) portfolioUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(Const.TRADE)) {
            ArrayList < Integer > ranButtons = tradeUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) tradeUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(Const.CREATORS)) {
            ArrayList < Integer > ranButtons = creatorsUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) creatorsUI.getMenuItem(ranButtonIndex));
                if (ranButton.checkButtonType(mouseEvent)) {
                    runButtonFunction(ranButton.getButtonFunction());
                }
            }
        } else if (state.equalsIgnoreCase(Const.ABOUT)) {
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
        if (buttonFunction.equalsIgnoreCase(Const.START)) {
            state = Const.START;
            window.setContentPane(startUI);
            startUI.requestFocus();
        } else if (buttonFunction.equalsIgnoreCase(Const.PORTFOLIO)) {
            state = Const.PORTFOLIO;
            window.setContentPane(portfolioUI);
            portfolioUI.requestFocus();
            ((Button) portfolioItems[3]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.TRADE)) {
            state = Const.TRADE;
            tradeUI.requestFocus();
            window.setContentPane(tradeUI);
            searchField.requestFocusInWindow();
            ((Button) tradeItems[4]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.CREATORS)) {
            state = Const.CREATORS;
            window.setContentPane(creatorsUI);
            creatorsUI.requestFocus();
            ((Button) creatorsItems[5]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.ABOUT)) {
            state = Const.ABOUT;
            window.setContentPane(aboutUI);
            aboutUI.requestFocus();
            ((Button) aboutItems[6]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.SEARCH)) {
            search = searchField.getText().toUpperCase();
            System.out.println(search);
            //  Graph Initialization
            Stock newStock = new Stock(search);
            if (chart == null) {
                chart = new LineChart(newStock);
            } else {
                chart.setStock(newStock);
            }
            chart.setPreferredSize(new Dimension(600, 400));
            chart.setBounds(Const.WIDTH - 600 - 50, 280, 600, 400);
            // System.out.println(newStock.getTicker() + newStock.getPrice() + newStock.getChange() + newStock.getChangePercentage());
            //  Adding Chart to Trade UI
            tradeUI.add(chart);
        }
    }
    //----------------------------------------------------------------------------
    //  Graphical User Interfaces
    GraphicalUserInterfaceItem[] startItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(250, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(400, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(600, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };

    GraphicalUserInterfaceItem[] portfolioItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(250, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(400, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(600, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };

    GraphicalUserInterfaceItem[] tradeItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(250, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(400, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(600, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true),
        new Text(50, 250, "Symbol", Const.ARIAL_FONT, Const.DARKGREY, true),
        new Button(new Rect(555, 305, Const.WHITE, 100, 50), new Text(600, 100, Const.SEARCH, Const.MENU_FONT_S, Const.PRIMARYBLACK, true), MouseEvent.MOUSE_CLICKED, Const.SEARCH, Const.LIGHTBLUE, true, true),
        new Image(555, 280, Const.SEARCH_ICON_IMAGE)
    };

    GraphicalUserInterfaceItem[] creatorsItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(250, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(400, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(600, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };

    GraphicalUserInterfaceItem[] aboutItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.MENU_FONT_XL, Const.WHITE, true),
        new Text(195, 15, "Scraper", Const.MENU_FONT_XL_BOLD, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(250, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(400, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(600, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.MENU_FONT_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };
    //----------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
        StockMarketSimulator simulator = new StockMarketSimulator();
        simulator.run();
    }
}