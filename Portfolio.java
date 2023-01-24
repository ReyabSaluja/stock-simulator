/**
 *  The Portfolio class is an object that represents the user's portfolio
 * 
 *  Their portfolio consists of all of their holdings, thus the ArrayList of holdings that defines it
 * 
 *  @author     Shawn Chen
 * 	@see		Holding.java
 *  @version    01/17/2023
 */

import java.util.ArrayList;

public class Portfolio {
    private ArrayList < Holding > portfolio;
    //----------------------------------------------------------------------------
    public Portfolio(ArrayList < Holding > portfolio) {
        this.portfolio = portfolio;
    }
    //----------------------------------------------------------------------------
    //  Getters and Setters
    public ArrayList < Holding > getPortfolio() {
        return this.portfolio;
    }
    //----------------------------------------------------------------------------
	/**
	 *	Eliminate holdings from the portfolio if they have zero quantity
     *
	 *	It goes through each order in the portfolio and checks if the quantity of the order 
	 *	is zero, if so it removes the order from the portfolio.
	 */
    public void eliminateShellOrders() {
        for (int orderIndex = 0; orderIndex < portfolio.size(); orderIndex++) {
            if (portfolio.get(orderIndex).getQuantity() == 0) {
                portfolio.remove(orderIndex);
            }
        }
    }

	/**
	 * 	The method updatePortfolio is used to update the user's portfolio with the given order.
	 * 
	 *	It first checks if the order corresponds to an existing holding in the portfolio. 
	 *	If so, it updates the holding with the order details.
	 *
	 *	If no existing holding is found for the order, it creates 
	 *	a new holding for the order and adds it to the portfolio.
	 *
	 *	Finally, it eliminates any holding with zero quantity in the portfolio by calling the eliminateShellOrders method.
	 * 
	 * 	@param order	the order to update the portfolio with
	 */
    public void updatePortfolio(Order order) {

        boolean holdingExists = false;
        for (int portfolioIndex = 0; portfolioIndex < portfolio.size(); portfolioIndex++) {
            if (portfolio.get(portfolioIndex).getStock().equals(order.getTicker())) {
                portfolio.get(portfolioIndex).updateHolding(order);
                holdingExists = true;
            }
        }

        if (holdingExists == false) {
            portfolio.add(new Holding(order));
        }

        eliminateShellOrders();
    }

    /**
     *  Deconustrict print provides the user's stock transactions in an orderly manner
     *  
     *  On every new line, a new holding is shown which makes it very easy for debugging, etc.
     */
    public void deconstructPrint() {
        if (portfolio.size() > 0) {
            // String returnStr = "";
            for (int portfolioIndex = 0; portfolioIndex < portfolio.size(); portfolioIndex++) {
                Holding holdingAt = portfolio.get(portfolioIndex);
                String returnStr = holdingAt.getHoldingType() + "/" + holdingAt.getStock() + "/" + Math.abs(holdingAt.getQuantity()) + "/" + holdingAt.getPrice() + ":";
                System.out.print(returnStr.substring(0, returnStr.length() - 1) + "\n");
            }
        }
    }
    
    /**
     *  Get's all of the user's holdings in a singular line seperated by semicolons
     * 
     *  @return  an ArrayList of all the user's current holdings
     */
    public String deconstruct() {
        if (portfolio.size() > 0) {
            String returnStr = "";
            for (int holdingIndex = 0; holdingIndex < portfolio.size(); holdingIndex++) {
                Holding holdingAt = portfolio.get(holdingIndex);
                returnStr += holdingAt.getHoldingType() + "/" + holdingAt.getStock() + "/" + Math.abs(holdingAt.getQuantity()) + "/" + holdingAt.getPrice() + ":";
            }

            return returnStr.substring(0, returnStr.length() - 1);
        } else {
            return "";
        }
    }
    //----------------------------------------------------------------------------
}