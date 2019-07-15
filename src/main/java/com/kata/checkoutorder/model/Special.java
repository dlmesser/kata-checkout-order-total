package com.kata.checkoutorder.model;

import java.math.BigDecimal;
import java.util.HashMap;

public abstract class Special {
	
	protected String productName;
	protected BigDecimal productPrice;
	
	/** The variable N in 'Buy N get M %X'*/
	protected int numProductPer;
	
	/** The variable M in 'Buy N get M %X'
	 *  Default 1 for ExactPriceSpecials
	 */
	protected int numDiscountedPer = 1;
	
	protected Special(String productName, BigDecimal productPrice, int numProductsRequired, int numDiscounted) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.numProductPer = numProductsRequired;
		this.numDiscountedPer = numDiscounted;
	}
	
	public abstract BigDecimal determineDiscountPriceDifference();
	
	/**
	 * Determine the number of times the special applies based on the product counts and calculate
	 * new discounted total accordingly.
	 * 
	 * @param productCounts - represents number of products per product name;
	 * @param preTaxTotal - the full price of products prior to applying specials (markdowns already included)
	 * @return new discounted total
	 */
	
	public BigDecimal updateTotalWithSpecialDiscountValue(HashMap<String, Integer> productCounts, BigDecimal preTaxTotal) {
		//if we have any of that item special applies to
		if(productCounts.containsKey(this.productName)) {
			int currentProductCount = productCounts.get(this.productName);
			int numberOfProductRequiredToGetDiscount = this.numProductPer;
			//if we have enough of that item to fulfill special requirements
			if(numberOfProductRequiredToGetDiscount < currentProductCount) {
				int currentItemsDiscounted = 0;
				int currentItemsFullPrice = 0;
				for (int i = 0; i < currentProductCount; i++) {
					//for every N at full price, discount M
					if (currentItemsFullPrice != 0 && currentItemsFullPrice % numberOfProductRequiredToGetDiscount == 0) {
						for(int j = 0; j < this.numDiscountedPer; j++) {
							//make sure to not mark more discounted than exist....
							if (i < currentProductCount) {
								currentItemsDiscounted++;
								i++;
							}
						}
					}
					
					currentItemsFullPrice++;
				}
				
				//subtract discounted price difference * number that should have been discounted from price total
				BigDecimal dicountPriceDifference = this.determineDiscountPriceDifference();
				preTaxTotal = preTaxTotal.subtract(dicountPriceDifference.multiply(new BigDecimal(currentItemsDiscounted)));
			}
		}
		
		return preTaxTotal;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getNumProductPer() {
		return numProductPer;
	}

	public void setNumProductPer(int numFullPriceProducts) {
		this.numProductPer = numFullPriceProducts;
	}

	public int getNumDiscountedPer() {
		return numDiscountedPer;
	}

	public void setNumDiscountedPer(int numDiscounted) {
		this.numDiscountedPer = numDiscounted;
	}
	
	public BigDecimal getProductPrice() {
		return productPrice;
	}
}
