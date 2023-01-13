public class StockEntry {
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjclose;
    private String volume;
    //----------------------------------------------------------------------------
    public StockEntry(String date, String open, String high, String low, String close, String adjclose, String volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjclose = adjclose;
        this.volume = volume;
    }
    //----------------------------------------------------------------------------
    //  Getters and Setters
    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return this.high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return this.low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return this.close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getAdjclose() {
        return this.adjclose;
    }

    public void setAdjclose(String adjclose) {
        this.adjclose = adjclose;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }    
    //----------------------------------------------------------------------------
    public String toString() {
        return  "{ Date: " + this.date 
              + "; Open: " + this.open 
              + "; High: " + this.high 
              + "; Low: " + this.low 
              + "; Close: " + this.close 
              + "; Adjclose: " + adjclose 
              + "; Volume: " + volume 
              + " }";
    }
}
