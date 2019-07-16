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

	@Override
	public BigDecimal determineDiscountProductCount(int productCount) {
		
		limit = this.hasProductLimit ? limit : productCount;
		
		int currentItemsDiscounted = 0;
		
		for (int i = 1; i <= productCount; i++) {
			//for every N at full price get for price X
			if (i != 0 && i % this.numProductPer == 0) {
				if (i <= limit) {
					currentItemsDiscounted++;
				}
			}
		}
		return new BigDecimal(currentItemsDiscounted);
	}

}
