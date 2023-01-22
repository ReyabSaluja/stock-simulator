public class Order {
	
	private String orderType;
	private String ticker;
	private int quantity;
	private double price; 
	private double orderAmount;
	
	public Order(String orderType, String ticker, String quantity, String price) {
		this.orderType = orderType;
		this.ticker = ticker;
		
		if (orderType.equals("BUY")) {
			this.quantity = Integer.parseInt(quantity);
		} else {
			this.quantity = Integer.parseInt(quantity) * -1;
		}
		this.price = Double.parseDouble(price);
		this.orderAmount = (double)(this.quantity) * this.price;
	}
	
	public double getorderAmount() {
		return orderAmount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
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
}
