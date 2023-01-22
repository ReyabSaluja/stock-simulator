import java.util.ArrayList;

public class Portfolio {
	
	private ArrayList<Holding> portfolio;
	public Portfolio(ArrayList<Holding> portfolio) {
		this.portfolio = portfolio;
	}
	
	//If a holding has a 0 quantity, then it will be removed from the portfolio
	public void eliminateShellOrders() {
		for (int i = 0; i < portfolio.size(); i++) {
			if (portfolio.get(i).getQuantity() == 0) {
				portfolio.remove(i);
			}
		}
	}
	
	public void updatePortfolio(Order order) {
		
		boolean holdingExists = false;
		for (int i = 0; i < portfolio.size(); i++) {
			if (portfolio.get(i).getStock().equals(order.getTicker())) {
				portfolio.get(i).updateHolding(order);
				holdingExists = true;
			}
		}
		
		if (holdingExists == false) {
			portfolio.add(new Holding(order));
		}
		
		eliminateShellOrders();
	}
	
	public ArrayList<Holding> getPortfolio() {
		return this.portfolio;
	}
	
	public String deconstruct() {
		if (portfolio.size() > 0) {
			String returnStr = "";
			for (int i = 0; i < portfolio.size(); i++) {
				Holding holdingAt = portfolio.get(i);
				returnStr += holdingAt.getHoldingType() + "/" + holdingAt.getStock() + "/" + Math.abs(holdingAt.getQuantity()) + "/" + holdingAt.getPrice() + ":";
			}
		
			return returnStr.substring(0, returnStr.length() - 1);
		} else {
			return "";
		}
	}
	
}
