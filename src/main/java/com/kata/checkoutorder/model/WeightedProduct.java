package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * Subclass of Product that has a weight to account for different price calculation.
 * @author Derek Messer
 *
 */
public class WeightedProduct extends Product {
	BigDecimal pounds;
	
	public WeightedProduct(String name, BigDecimal price, BigDecimal pounds) {
		super(name, price);
		this.pounds = pounds;
	}
	
	/**
	 * Multiplies the price/weight by weight to get final price.
	 * 
	 * returns BigDecimal price of item with weight;
	 */
	@Override
	public BigDecimal getPrice() {
		return (price.subtract(markdown)).multiply(pounds);
	}

}
