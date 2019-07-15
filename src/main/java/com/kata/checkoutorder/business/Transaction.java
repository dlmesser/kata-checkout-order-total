package com.kata.checkoutorder.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kata.checkoutorder.model.Product;
import com.kata.checkoutorder.model.Special;

/**
 * Transaction class that represents the API for scanning
 * items and calculating pre-tax totals in a checkout order. 
 * 
 * @author Derek Messer
 *
 */
public class Transaction {

	private HashMap<String, Integer> productCounts;
	private List<Product> products;
	private List<Special> specials;
	private BigDecimal total;
	
	public Transaction() {
		productCounts = new HashMap<String, Integer>();
		products = new ArrayList<Product>();
		specials = new ArrayList<Special>();
		total = new BigDecimal(0);
	}
	
	/**
	 * Adds a product to the current transaction.
	 * @param item - the product being scanned.
	 */
	public void scanProduct(Product item) {
		products.add(item);
		
		String itemName = item.getName();
		if(productCounts.containsKey(itemName)) {
			productCounts.put(itemName, productCounts.get(itemName) + 1);
		} else {
			productCounts.put(itemName, 1);
		}
		
		updateTotal();
	}
	
	/**
	 * This method is called whenever something is modified within a transaction that could effect total cost.
	 * 
	 * First we add full price of all products in the transaction accounting for original prices and markdowns.
	 * Then we apply any specials on products and reduce the total by the discounted value.
	 * 
	 */
	public void updateTotal() {
		BigDecimal preTaxTotal = new BigDecimal(0);
		

		for(Product item : products) {
			preTaxTotal = preTaxTotal.add(item.getPrice());
		}
		
		for(Special special : specials) {
			//if we have any of that item special applies to
			if(productCounts.containsKey(special.getProductName())) {
				int currentProductCount = productCounts.get(special.getProductName());
				int numberOfProductRequiredToGetDiscount = special.getNumProductPer();
				//if we have enough of that item to fulfill special requirements
				if(numberOfProductRequiredToGetDiscount < currentProductCount) {
					int currentItemsDiscounted = 0;
					int currentItemsFullPrice = 0;
					for (int i = 0; i < currentProductCount; i++) {
						//for every N at full price, discount M
						if (currentItemsFullPrice != 0 && currentItemsFullPrice % numberOfProductRequiredToGetDiscount == 0) {
							for(int j = 0; j < special.getNumDiscountedPer(); j++) {
								//make sure to not mark more discounted than exist....
								if (i < currentProductCount) {
									currentItemsDiscounted++;
									i++;
								}
							}
						}
						
						currentItemsFullPrice++;
					}
					
					//subtract discounted price * number that should have been discounted from price total
					BigDecimal percentOffPrice = special.getProductPrice().multiply(special.getPercentOffAsDecimal());
					preTaxTotal = preTaxTotal.subtract(percentOffPrice.multiply(new BigDecimal(currentItemsDiscounted)));
				}
			}
		}
		
		
		total = preTaxTotal;
	}

	/**
	 * Adds a special to the current transaction.
	 * @param special - a discount rule for a particular item
	 */
	public void addSpecial(Special special) {
		specials.add(special);
		updateTotal();
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public List<Product> getProducts() {
		return products;
	}

}
