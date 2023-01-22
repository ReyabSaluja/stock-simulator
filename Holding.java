
public class Holding {
	
    private String holdingType;
    private String stock;
    private int quantity;
    private double price;
    private double holdingAmount;
    
     public Holding(String holdingType, String stock, String quantity, String price) {
         this.holdingType = holdingType;
         this.stock = stock;
         if (this.holdingType.equals("BUY")) {
             this.quantity = Integer.parseInt(quantity);
         } else {
             this.quantity = Integer.parseInt(quantity) * -1;
         }
         this.price = Double.parseDouble(price);
         this.holdingAmount = ((double)(this.quantity)) * this.price;
     }
     
     public Holding(Order initializeHolding) {
         this.holdingType = initializeHolding.getOrderType();
         this.stock = initializeHolding.getTicker();
         this.quantity = initializeHolding.getQuantity();
         this.price = initializeHolding.getPrice();
         this.holdingAmount = ((double)(this.quantity)) * this.price;
     }
     
     
     public boolean verifyOrder(Order order) {
         if (order.getTicker().equals(this.stock)) {
             return true;
         } else {
             return false;
         }
     }
     
     //Precondition is that order is the same stock as holding
     public void updateHolding (Order order) {
         this.quantity = this.quantity + order.getQuantity();
         this.holdingAmount = this.holdingAmount + order.getorderAmount();
     }
 
     public String getHoldingType() {
         return holdingType;
     }
 
     public void setHoldingType(String holdingType) {
         this.holdingType = holdingType;
     }
 
     public String getStock() {
         return stock;
     }
 
     public void setStock(String stock) {
         this.stock = stock;
     }
 
     public int getQuantity() {
         return quantity;
     }
 
     public void setQuantity(int quantity) {
         this.quantity = quantity;
     }
 
     public double getPrice() {
         return price;
     }
 
     public void setPrice(double price) {
         this.price = price;
     }
 
     public double getHoldingAmount() {
         return holdingAmount;
     }
 
     public void setHoldingAmount(double holdingAmount) {
         this.holdingAmount = holdingAmount;
     }
 }
 