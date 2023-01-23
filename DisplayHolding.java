import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class DisplayHolding extends DisplayItem {
    private ArrayList<Text> toGraph;
    private Portfolio portfolio;
    DisplayHolding(int x, int y, Color color, Portfolio portfolio) {
        super(x, y, color);
        this.portfolio = portfolio;
        toGraph = new ArrayList<Text>();
    }

    public void displayHolding(Holding stockPosition, int rowXCoordinate) {
        int yPosition = 480;
        ArrayList<String> holdComponents = stockPosition.displayPosition();
        for (int i = 0; i < 4; i++) {
            toGraph.add(new Text(rowXCoordinate, yPosition, holdComponents.get(i), Const.OPENSANS_BOLD_S, Const.WHITE, true));
            yPosition += 100;
        }
    }

    public void displayPortfolio() {
        int xPosition = 80;
        for (int i = 0; i < portfolio.getPortfolio().size(); i++) {
            displayHolding(portfolio.getPortfolio().get(i), xPosition);
        }
    }

    public ArrayList<Text> getTextPosition() {
        return toGraph;
    }

    public void debug() {
        for (int i = 0; i < toGraph.size(); i++) {
            System.out.println("Text: " + toGraph.get(i).getText());
            System.out.println("XPosition: " + toGraph.get(i).getX());
            System.out.println("YPosition: " + toGraph.get(i).getY());
        }
    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub
        
        
    }
    
}
