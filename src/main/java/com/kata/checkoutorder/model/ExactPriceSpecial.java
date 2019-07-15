package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * The ExactPriceSpecial model represents the variables for the equation
 * 'Buy N for X" for a specific Product. 
 * 
 * @author Derek Messer
 *
 */
public class ExactPriceSpecial extends Special {
	private BigDecimal newPrice;
	
	
	public ExactPriceSpecial(String productName, BigDecimal productPrice, int numProductsRequired, 
			BigDecimal newPrice) {
		super(productName, productPrice, numProductsRequired, 1);
		this.newPrice = newPrice;
		
	}
	
	@Override
	public BigDecimal determineDiscountPriceDifference() {
		return this.productPrice.multiply(new BigDecimal(numProductPer)).subtract(newPrice);
	}

}
