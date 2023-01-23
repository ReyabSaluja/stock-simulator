import java.util.ArrayList;

import java.net.Socket;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

public class StockMarketSimulator {
    //  General Display Components
    private JFrame window;
    private MenuMouseListener mouseListener;
    private MenuMotionListener mouseMotionListener;
    private Display loginUI, portfolioUI, tradeUI, creatorsUI, aboutUI;
    //  State
    private String state = Const.START;
    //  Login Display Components
    private JTextField usernameField;
    private JTextField passwordField;
    private boolean authenticated;
    //  Portfolio Components
    private Portfolio portfolio;
    private JTextField accountValueDisplay;
    private JTextArea holdings;
    //  Trade Display Components
    private JTextField searchField;
    private String search;
    private LineChart chart;
    private JComboBox <String> actionField;
    private String[] actions;
    private JTextField quantityField;
    //  About Display Components
    private JTextArea about;
    //  Creators Display Components
    private JTextArea creators;
    //  User Save Components
    private String userInformation;
    private PrintWriter output;
    private BufferedReader input;
    //  Connection Components
    private final String LOCAL_HOST;
    private final int PORT;
    private Socket clientSocket;
    private ConnectionTerminator connectionTerminator;
    //  User Components
    private double accountValue;
    //----------------------------------------------------------------------------
    public StockMarketSimulator() {
        //  Network Initialization
        LOCAL_HOST = "127.0.0.1";
        PORT = 5001;
        authenticated = false;
        //  Account Initialization
        accountValue = 0;
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
        loginUI = new Display(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, startItems);
        portfolioUI = new Display(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, portfolioItems);
        tradeUI = new Display(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, tradeItems);
        creatorsUI = new Display(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, creatorsItems);
        aboutUI = new Display(0, 0, Const.WIDTH, Const.HEIGHT, Const.WHITE, aboutItems);
        //  Search Field Initialization
        searchField = new JTextField("", 15);
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                searchField.setText("   Look up Company Symbol");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        //  Adding Search Field to Trade UI
        tradeUI.setLayout(null);
        searchField.setBounds(50, 275, 500, 70);
        searchField.setForeground(Const.LIGHTGREY);
        searchField.setBorder(new LineBorder(Const.LIGHTBLUE, 3, true));
        tradeUI.add(searchField);
        //  Username Field Initialization
        userInformation = "";
        usernameField = new JTextField("", 15);
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                usernameField.setText("   Username");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        //  Password Field Initialization
        passwordField = new JTextField("", 15);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText("   Password");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        loginUI.setLayout(null);
        //  Username Field Placement
        usernameField.setBounds(Const.WIDTH / 2 - 250, 250, 500, 70);
        usernameField.setForeground(Const.LIGHTGREY);
        usernameField.setBorder(new LineBorder(Const.LIGHTBLUE, 3, true));
        loginUI.add(usernameField);
        //  Password Field Placement
        passwordField.setBounds(Const.WIDTH / 2 - 250, 350, 500, 70);
        passwordField.setForeground(Const.LIGHTGREY);
        passwordField.setBorder(new LineBorder(Const.LIGHTBLUE, 3, true));
        loginUI.add(passwordField);
        //  Action Field Initialization
        actions = new String[] {
            "BUY",
            "SELL"
        };
        actionField = new JComboBox <String> (actions);
        //  Action Field Placement
        actionField.setBounds(50, 385, 200, 50);
        actionField.setForeground(Const.PRIMARYBLACK);
        tradeUI.add(actionField);
        //  Quantity Field
        quantityField = new JTextField("", 15);
        quantityField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText("   Quantity");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        //  Quantity Field Placement
        quantityField.setBounds(50, 475, 500, 70);
        quantityField.setForeground(Const.LIGHTGREY);
        quantityField.setBorder(new LineBorder(Const.LIGHTBLUE, 3, true));
        tradeUI.add(quantityField);
        //  Holding Field
        portfolioUI.setLayout(null);
        holdings = new JTextArea();
        holdings.setEditable(false);
        holdings.setBackground(Const.DARKBLUE);
        holdings.setBounds(80, 500, Const.WIDTH - 200, 200);
        holdings.setFont(Const.OPENSANS_BOLD_S);
        holdings.setForeground(Const.WHITE);
        portfolioUI.add(holdings);
        //  Account Value Field
        accountValueDisplay = new JTextField();
        accountValueDisplay.setEditable(false);
        accountValueDisplay.setBackground(Const.DARKBLUE);
        accountValueDisplay.setBounds(70, 325, 175, 75);
        accountValueDisplay.setFont(Const.OPENSANS_BOLD_M);
        accountValueDisplay.setForeground(Const.WHITE);
        portfolioUI.add(accountValueDisplay);
        //  About Display
        aboutUI.setLayout(null);
        about = new JTextArea();
        about.setEditable(false);
        about.setBounds(220, 325, Const.WIDTH - 200, 700);
        about.setFont(Const.OPENSANS_BOLD_S);
        about.setForeground(Const.PRIMARYBLACK);
        aboutUI.add(about);
        //  Creators Display
        creatorsUI.setLayout(null);
        creators = new JTextArea();
        creators.setEditable(false);
        creators.setBounds(250, 325, Const.WIDTH - 200, 700);
        creators.setFont(Const.OPENSANS_BOLD_S);
        creators.setForeground(Const.PRIMARYBLACK);
        creatorsUI.add(creators);
        //  Connection
        connectionTerminator = new ConnectionTerminator();
        window.addWindowListener(connectionTerminator);
        //  Window
        window.setContentPane(loginUI);
        window.setVisible(true);
    }
    //----------------------------------------------------------------------------
    public void run() throws Exception {
        //	Main Game Loop
        start();
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
    public void start() throws Exception {
        System.out.println("ESTABLISHING CONNECTION TO SERVER---------------");
        clientSocket = new Socket(LOCAL_HOST, PORT);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("CONNECTION ESTABLISHED--------------------------");
    }
    //----------------------------------------------------------------------------
    public void stop() throws Exception {
        System.out.println("CLOSING CONNECTION TO SERVER-------------------");
        clientSocket.close();
        output.close();
        input.close();
        System.out.println("CONNECTION CLOSED-------------------------------");
    }
    //----------------------------------------------------------------------------
    public Portfolio buildPortfolio(String inputString) {
        if (inputString.length() > 0) {
            ArrayList < Holding > returnPortfolio = new ArrayList < Holding > ();
            String[] portfolioComponents = inputString.split(":");
            for (int i = 0; i < portfolioComponents.length; i++) {
                String[] holdingComponents = portfolioComponents[i].split("/");
                returnPortfolio.add(new Holding(holdingComponents[0], holdingComponents[1], holdingComponents[2], holdingComponents[3]));
            }
            return new Portfolio(returnPortfolio);
        } else {
            return new Portfolio(new ArrayList < Holding > ());
        }
    }

    public void synchronizeAccountValue() {
        accountValue = 0;
        String displayString = "";
        for (int i = 0; i < portfolio.getPortfolio().size(); i++) {
            accountValue += portfolio.getPortfolio().get(i).getHoldingAmount();
        }

        accountValueDisplay.setText("$" + Double.toString(accountValue));
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
    public class ConnectionTerminator implements WindowListener {
        public void windowClosing(WindowEvent e) {
            try {
                stop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        public void windowOpened(WindowEvent e) { // MUST be implemented even if not used!
        }
        public void windowClosed(WindowEvent e) { // MUST be implemented even if not used!
        }
        public void windowIconified(WindowEvent e) { // MUST be implemented even if not used!
        }
        public void windowDeiconified(WindowEvent e) { // MUST be implemented even if not used!
        }
        public void windowActivated(WindowEvent e) { // MUST be implemented even if not used!
        }
        public void windowDeactivated(WindowEvent e) { // MUST be implemented even if not used!
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
            loginUI.resetButtons();

            ArrayList < Integer > hoveredButtons = loginUI.findCollidedButtons(mouseX, mouseY);

            for (int hoveredButtonIndex: hoveredButtons) {
                Button hoveredButton = ((Button) loginUI.getMenuItem(hoveredButtonIndex));

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
            ArrayList < Integer > ranButtons = loginUI.findCollidedButtons(mouseX, mouseY);

            for (int ranButtonIndex: ranButtons) {
                Button ranButton = ((Button) loginUI.getMenuItem(ranButtonIndex));
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
            /*
             *  Setting state and current display panel
             *  based on what button was ran
             */
            state = Const.START;                        
            window.setContentPane(loginUI);
            loginUI.requestFocus();
        } else if (buttonFunction.equalsIgnoreCase(Const.PORTFOLIO) && authenticated) {
            state = Const.PORTFOLIO;
            window.setContentPane(portfolioUI);
            portfolioUI.requestFocus();
            ((Button) portfolioItems[3]).setTextColor(Const.LIGHTBLUE); //  Keeping current state menu option colored when currently on it            
        } else if (buttonFunction.equalsIgnoreCase(Const.TRADE) && authenticated) {
            state = Const.TRADE;
            tradeUI.requestFocus();
            window.setContentPane(tradeUI);
            searchField.requestFocusInWindow();
            ((Button) tradeItems[4]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.CREATORS) && authenticated) {
            creators.setText("This Stock Simulator was made entirely by Reyab Saluja & Shawn Chen \n It was developed for our ICS4UE final project and we're very proud of it :)");

            state = Const.CREATORS;
            window.setContentPane(creatorsUI);
            creatorsUI.requestFocus();
            ((Button) creatorsItems[5]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.ABOUT) && authenticated) {
            about.setText("\n This is a real-time stock simulator integrated with dynamic charts, developed fully in Java. \n When placing an order, it will be added to the stock holding into your portfolio \n ");

            state = Const.ABOUT;
            window.setContentPane(aboutUI);
            aboutUI.requestFocus();
            ((Button) aboutItems[6]).setTextColor(Const.LIGHTBLUE);
        } else if (buttonFunction.equalsIgnoreCase(Const.SEARCH)) {
            search = searchField.getText().toUpperCase();
            //  Graph Initialization
            Stock newStock = new Stock(search);
            if (chart == null) {
                chart = new LineChart(newStock);
            } else {
                chart.setStock(newStock);
            }
            chart.setPreferredSize(new Dimension(600, 400));
            chart.setBounds(Const.WIDTH - 600 - 50, 280, 600, 400);
            //  Adding Chart to Trade UI
            tradeUI.add(chart);
        } else if (buttonFunction.equalsIgnoreCase(Const.LOGIN)) {
            String loginStatus = "";
            String portfolioInput = "";
            String username = usernameField.getText();
            String password = passwordField.getText();
            String userInformation = username + "/" + password;
            output.println(userInformation);
            output.flush();
            try {
                loginStatus = input.readLine();
            } catch (IOException e) {
                System.out.println("Error!");
            }

            if (loginStatus.equalsIgnoreCase("Authenticated!")) {
                authenticated = true;
            }

            try {
                portfolioInput = input.readLine();
            } catch (IOException e) {
                System.out.println("Error!");
            }

            portfolio = buildPortfolio(portfolioInput);
            portfolio.deconstructPrint();

            if (portfolio != null) {
                String toDisplay = "";
                String toDisplayString = "";
                for (int i = 0; i < portfolio.getPortfolio().size(); i++) {
                    Holding stockPosition = portfolio.getPortfolio().get(i);
                    toDisplay = "Stock Ticker: " + stockPosition.getStock() + " Stock Holding Position: " + stockPosition.getHoldingType() + " Stock Quantity: " + stockPosition.getQuantity() + " Stock Holding Amount ($)" + stockPosition.getHoldingAmount() + "\n";  
                    toDisplayString += toDisplay;
                }

                holdings.setText(toDisplayString);
            }

            synchronizeAccountValue();

            state = Const.PORTFOLIO;
            window.setContentPane(portfolioUI);
            portfolioUI.requestFocus();
        } else if (buttonFunction.equalsIgnoreCase(Const.SUBMIT)) {
            String updatedPortfolio = "";
            String ticker = searchField.getText();
            String quantity = quantityField.getText();
            String orderType = actionField.getSelectedItem().toString();
            Stock submitStock = new Stock(ticker);
            ArrayList < Double > submitStockPrices = submitStock.getClosingPrices();
            String submitStockPrice = submitStockPrices.get(submitStockPrices.size() - 1).toString();
            String orderInformation = orderType + "/" + ticker + "/" + quantity + "/" + submitStockPrice;
            output.println(orderInformation);
            System.out.println("Sent!");
            output.flush();
            try {
                updatedPortfolio = input.readLine();
            } catch (IOException e) {
                System.out.println("Error!");
            }
            portfolio = buildPortfolio(updatedPortfolio);
            synchronizeAccountValue();
            System.out.println(portfolio.deconstruct());

        }
    }
    //----------------------------------------------------------------------------
    //  Graphical User Interfaces
    DisplayItem[] startItems = {
        //  Login button BELOW the text fields
        new Text(570, 130, "Stock", Const.OPENSANS_BOLD_M, Const.PRIMARYBLACK, true),
        new Text(665, 130, "Simulator", Const.OPENSANS_BOLD_M, Const.DARKBLUE, true),
        new Button(new Rect(630, 450, Const.DARKBLUE, 150, 50), new Text(60, 180, "", Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.LOGIN, Const.LIGHTBLUE, false, false),
        new Text(660, 450, "Sign In", Const.OPENSANS_BOLD_S, Const.WHITE, true),
        new Text(Const.WIDTH / 2 - 250, 200, "Log In", Const.OPENSANS_S, Const.PRIMARYBLACK, true)
    };

    DisplayItem[] portfolioItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.OPENSANS_BOLD_L, Const.WHITE, true),
        new Text(185, 15, "Simulator", Const.OPENSANS_BOLD_L, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(225, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(350, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(525, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true),
        new Rect(50, 275, Const.DARKBLUE, 400, 150),
        new Text(70, 305, "ACCOUNT VALUE", Const.OPENSANS_BOLD_XS, Const.WHITE, true),
        new Rect(50, 450, Const.DARKBLUE, Const.WIDTH - 100, 300),
        new Text(80, 470, "Symbol", Const.OPENSANS_BOLD_XS, Const.WHITE, true)
    };

    DisplayItem[] tradeItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.OPENSANS_BOLD_L, Const.WHITE, true),
        new Text(185, 15, "Simulator", Const.OPENSANS_BOLD_L, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(225, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(350, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(525, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true),
        new Text(50, 245, "Symbol", Const.OPENSANS_XXS, Const.DARKGREY, true),
        new Button(new Rect(555, 305, Const.WHITE, 100, 50), new Text(600, 100, Const.SEARCH, Const.MYRIAD_S, Const.PRIMARYBLACK, true), MouseEvent.MOUSE_CLICKED, Const.SEARCH, Const.LIGHTBLUE, true, true),
        new Image(555, 280, Const.SEARCH_ICON_IMAGE),
        new Text(50, 355, "Action", Const.OPENSANS_XXS, Const.DARKGREY, true),
        new Text(50, 445, "Quantity", Const.OPENSANS_XXS, Const.DARKGREY, true),
        new Button(new Rect(50, 580, Const.DARKBLUE, 130, 50), new Text(80, 160, Const.SUBMIT, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.SUBMIT, Const.LIGHTBLUE, false, true)
    };

    DisplayItem[] creatorsItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.OPENSANS_BOLD_L, Const.WHITE, true),
        new Text(185, 15, "Simulator", Const.OPENSANS_BOLD_L, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(225, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(350, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(525, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };

    DisplayItem[] aboutItems = {
        new Image(0, 0, Const.GUI_START_IMAGE),
        new Text(50, 15, "Stock", Const.OPENSANS_BOLD_L, Const.WHITE, true),
        new Text(185, 15, "Simulator", Const.OPENSANS_BOLD_L, Const.LIGHTBLUE, true),
        new Button(new Rect(50, 100, Const.DARKBLUE, 100, 50), new Text(50, 100, Const.PORTFOLIO, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.PORTFOLIO, Const.LIGHTBLUE, true, true),
        new Button(new Rect(225, 100, Const.DARKBLUE, 100, 50), new Text(250, 100, Const.TRADE, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.TRADE, Const.LIGHTBLUE, true, true),
        new Button(new Rect(350, 100, Const.DARKBLUE, 100, 50), new Text(400, 100, Const.CREATORS, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.CREATORS, Const.LIGHTBLUE, true, true),
        new Button(new Rect(525, 100, Const.DARKBLUE, 100, 50), new Text(600, 100, Const.ABOUT, Const.OPENSANS_BOLD_S, Const.WHITE, true), MouseEvent.MOUSE_CLICKED, Const.ABOUT, Const.LIGHTBLUE, true, true)
    };
    //----------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
        StockMarketSimulator simulator = new StockMarketSimulator();
        try {
            simulator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}