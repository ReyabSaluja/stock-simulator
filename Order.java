public class Order {
	
	private String orderType;
	private String ticker;
	private int quantity;
	private double price; 
	
	public Order(String orderType, String ticker, String quantity, String price) {
		this.orderType = orderType;
		this.ticker = ticker;
		this.quantity = Integer.parseInt(quantity);
		this.price = Double.parseDouble(price);
	}
	
	public boolean compareOrder(Order order) {
		if (order.getTicker().equals(this.ticker)) {
			return true;
		} else {
			return false;
		}
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
