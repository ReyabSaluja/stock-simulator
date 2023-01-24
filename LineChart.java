import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.Stroke;

/**
 *  LineChart is a display for a chart that draws a visualization panel for a stock in the form of a chart
 * 
 *  @see        Stock.java
 *  @author     Shawn Chen
 *  @version    01/05/2023
 */
public class LineChart extends JPanel {

    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List < Double > stockPrices;
    //----------------------------------------------------------------------------
    public LineChart(Stock stock) {
        this.stockPrices = stock.getClosingPrices();
    }
    //----------------------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.RED);
        g.fillRect(1375, 0, 25, 25);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * Const.PADDING) - Const.LABEL_PADDING) / (stockPrices.size() - 1);
        double yScale = ((double) getHeight() - 2 * Const.PADDING - Const.LABEL_PADDING) / (getHighestStockPrice() - getLowestStockPrice());

        List < Point > graphPoints = new ArrayList < > ();
        for (int i = 0; i < stockPrices.size(); i++) {
            int x1 = (int)(i * xScale + Const.PADDING + Const.LABEL_PADDING);
            int y1 = (int)((getHighestStockPrice() - stockPrices.get(i)) * yScale + Const.PADDING);
            graphPoints.add(new Point(x1, y1));
        }

        //  Draw White Background
        g2.setColor(Color.WHITE);
        g2.fillRect(Const.PADDING + Const.LABEL_PADDING, Const.PADDING, getWidth() - (2 * Const.PADDING) - Const.LABEL_PADDING, getHeight() - 2 * Const.PADDING - Const.LABEL_PADDING);
        g2.setColor(Color.BLACK);

        //  Create Hatch Marks and Grid Lines For y-axis
        for (int yDivisionIndex = 0; yDivisionIndex < numberYDivisions + 1; yDivisionIndex++) {
            int x0 = Const.PADDING + Const.LABEL_PADDING;
            int x1 = pointWidth + Const.PADDING + Const.LABEL_PADDING;
            int y0 = getHeight() - ((yDivisionIndex * (getHeight() - Const.PADDING * 2 - Const.LABEL_PADDING)) / numberYDivisions + Const.PADDING + Const.LABEL_PADDING);
            int y1 = y0;
            if (stockPrices.size() > 0) {
                g2.setColor(Const.GRID_COLOR);
                g2.drawLine(Const.PADDING + Const.LABEL_PADDING + 1 + pointWidth, y0, getWidth() - Const.PADDING, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int)((getLowestStockPrice() + (getHighestStockPrice() - getLowestStockPrice()) * ((yDivisionIndex * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        //  Create Hatch Marks and Grid Lines For x-axis
        for (int stockPriceIndex = 0; stockPriceIndex < stockPrices.size(); stockPriceIndex++) {
            if (stockPrices.size() > 1) {
                int x0 = stockPriceIndex * (getWidth() - Const.PADDING * 2 - Const.LABEL_PADDING) / (stockPrices.size() - 1) + Const.PADDING + Const.LABEL_PADDING;
                int x1 = x0;
                int y0 = getHeight() - Const.PADDING - Const.LABEL_PADDING;
                int y1 = y0 - pointWidth;
                if ((stockPriceIndex % ((int)((stockPrices.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(Const.GRID_COLOR);
                    g2.drawLine(x0, getHeight() - Const.PADDING - Const.LABEL_PADDING - 1 - pointWidth, x1, Const.PADDING);
                    g2.setColor(Color.BLACK);
                    String xLabel = stockPriceIndex + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        //  Create x-axis and y-axis
        g2.drawLine(Const.PADDING + Const.LABEL_PADDING, getHeight() - Const.PADDING - Const.LABEL_PADDING, Const.PADDING + Const.LABEL_PADDING, Const.PADDING);
        g2.drawLine(Const.PADDING + Const.LABEL_PADDING, getHeight() - Const.PADDING - Const.LABEL_PADDING, getWidth() - Const.PADDING, getHeight() - Const.PADDING - Const.LABEL_PADDING);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(Const.LINE_COLOR);
        g2.setStroke(Const.GRAPH_STROKE);
        for (int graphPointIndex = 0; graphPointIndex < graphPoints.size() - 1; graphPointIndex++) {
            int x1 = graphPoints.get(graphPointIndex).x;
            int y1 = graphPoints.get(graphPointIndex).y;
            int x2 = graphPoints.get(graphPointIndex + 1).x;
            int y2 = graphPoints.get(graphPointIndex + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(Const.POINT_COLOR);
        for (int graphPointIndex = 0; graphPointIndex < graphPoints.size(); graphPointIndex++) {
            int x = graphPoints.get(graphPointIndex).x - pointWidth / 2;
            int y = graphPoints.get(graphPointIndex).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }

    private double getLowestStockPrice() {
        double lowestPrice = Double.MAX_VALUE;
        for (Double price: stockPrices) {
            lowestPrice = Math.min(lowestPrice, price);
        }
        return lowestPrice;
    }

    private double getHighestStockPrice() {
        double highestPrice = Double.MIN_VALUE;
        for (Double price: stockPrices) {
            highestPrice = Math.max(highestPrice, price);
        }
        return highestPrice;
    }

    public void setStockPrices(List < Double > stockPrices) {
        this.stockPrices = stockPrices;
        invalidate();
        this.repaint();
    }

    public void setStock(Stock stock) {
        this.stockPrices = stock.getClosingPrices();
        invalidate();
        this.repaint();
    }

    public List < Double > getStockPrices() {
        return stockPrices;
    }
}