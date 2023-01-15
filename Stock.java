import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Stock {
    private String name;
    private String url;
    private ArrayList < StockEntry > stockEntries;
    //----------------------------------------------------------------------------
    public Stock(String name) {
        this.name = name;
        this.url = "https://finance.yahoo.com/quote/" + name + "/history?p=" + name;
        try {
            stockEntries = importEntries();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------
    //  Function to import all of the stock entries into the stock by webscraping them from Yahoo Finance
    private ArrayList < StockEntry > importEntries() throws IOException {
        Document doc = Jsoup.connect(url).get();                              		//  Connecting to the webpage via JSoup
		/*
		 *	Assigning the stock history to a variable using the stock history table HTML id
		 *	Assigning each row from the stock history table to a variable
		 */
        Element table = doc.getElementById("Col1-1-HistoricalDataTable-Proxy");
        Elements rows = table.select("tr");

        Elements first = rows.get(0).select("th, td");				//	Assigning the first row from the HTML table
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

    public ArrayList < Double > getClosingPrices() {
        ArrayList < Double > closingPrices = new ArrayList<>();
        for (int numClosingPrices = 0; numClosingPrices < this.stockEntries.size(); numClosingPrices++) {
            if (this.stockEntries.get(numClosingPrices).getClose() != null) {
                closingPrices.add(Double.parseDouble(this.stockEntries.get(numClosingPrices).getClose()));
            }
        }
        return closingPrices;
    }

    public void printStock() {
        for (int i = 0; i < this.stockEntries.size(); i++) {
            System.out.println(this.stockEntries.get(i));
        }
    }
}