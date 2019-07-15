package com.kata.checkoutorder.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kata.checkoutorder.model.Product;
import com.kata.checkoutorder.model.Special;
import com.kata.checkoutorder.model.PercentOffSpecial;

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
			preTaxTotal = special.updateTotalWithSpecialDiscountValue(productCounts, preTaxTotal);
		}
		
		
		total = preTaxTotal;
	}

	/**
	 * Adds a special to the current transaction.
	 * @param percentDiscount - a discount rule for a particular item
	 */
	public void addSpecial(Special percentDiscount) {
		specials.add(percentDiscount);
		updateTotal();
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public List<Product> getProducts() {
		return products;
	}

}
