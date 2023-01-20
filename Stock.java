import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Stock {
    private String ticker;
    private String price;
    private String change;
    private String changePercentage;
    private String url;

    private String[] stockInfo;
    private ArrayList < StockEntry > stockEntries;
    //----------------------------------------------------------------------------
    public Stock(String ticker) {
        this.ticker = ticker;
        this.url = "https://finance.yahoo.com/quote/" + ticker + "/history?p=" + ticker;
        try {
            this.stockEntries = importEntries();
            // this.stockInfo = importStockInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this.price = this.stockInfo[0];
        // this.change = this.stockInfo[1];
        // this.changePercentage = this.stockInfo[2];
    }
    //----------------------------------------------------------------------------
    //  Getters and Setters
    public StockEntry getStock() {
        for (int i = 0; i < this.stockEntries.size(); i++) {
            return this.stockEntries.get(i);
        }
        return null;
    }

    public String getTicker() {
        return this.ticker;
    }

    public String getPrice() {
        return this.price;
    }

    public String getChange() {
        return this.change;
    }

    public String getChangePercentage() {
        return this.changePercentage;
    }

    public ArrayList < Double > getClosingPrices() {
        ArrayList < Double > closingPrices = new ArrayList < > ();
        for (int numClosingPrices = 0; numClosingPrices < this.stockEntries.size(); numClosingPrices++) {
            if (this.stockEntries.get(numClosingPrices).getClose() != null) {
                closingPrices.add(Double.parseDouble(this.stockEntries.get(numClosingPrices).getClose()));
            }
        }
        Collections.reverse(closingPrices);
        return closingPrices;
    }
    //-----------------------------------------------------------------------------
    //  Function to import all of the stock entries into the stock by webscraping them from Yahoo Finance
    private ArrayList < StockEntry > importEntries() throws IOException {
        Document doc = Jsoup.connect(url).get(); //  Connecting to the webpage via JSoup
        /*
         *	Assigning the stock history to a variable using the stock history table HTML id
         *	Assigning each row from the stock history table to a variable
         */
        Element table = doc.getElementById("Col1-1-HistoricalDataTable-Proxy");
        Elements rows = table.select("tr");

        Elements first = rows.get(0).select("th, td"); //	Assigning the first row from the HTML table
        List < String > headers = new ArrayList < > ();

        ArrayList < StockEntry > stockEntries = new ArrayList < > ();

        for (Element header: first) {
            headers.add(header.text());
        }

        List < Map < String, String >> listMap = new ArrayList < Map < String, String >> ();
        for (int row = 1; row < rows.size() - 1; row++) {
            Elements colVals = rows.get(row).select("th, td");
            int colCount = 0;
            Map < String, String > tuple = new LinkedHashMap < String, String > ();
            for (Element colVal: colVals) {
                tuple.put(headers.get(colCount++), colVal.text());
                // System.out.println(tuple + "\n");
            }
            listMap.add(tuple);

            StockEntry currentEntry = new StockEntry(tuple.get("Date"), tuple.get("Open"), tuple.get("High"), tuple.get("Low"), tuple.get("Close*"), tuple.get("Adj Close**"), tuple.get("Volume"));

            // System.out.println(currentEntry.toString());

            stockEntries.add(currentEntry);
        }
        return stockEntries;
    }

    private String[] importStockInfo() throws IOException {
        Document doc = Jsoup.connect(url).get(); //  Connecting to the webpage via JSoup
        Element header = doc.getElementById("quote-header-info");
        // Get the stock price
        Element priceElement = header.selectFirst("e3b14781 e59c8479");
        String price = priceElement.text();

        // Get the stock change
        Element changeElement = header.selectFirst("Fw(500) Pstart(8px) Fz(24px)");
        String change = changeElement.text();

        // Get the stock change percentage
        Element changePercentageElement = header.selectFirst("Fw(500) Pstart(8px) Fz(24px)");
        String changePercentage = changePercentageElement.text();

        String[] stockInfo = {this.ticker, price, change, changePercentage};

        return stockInfo;
        
    }
}