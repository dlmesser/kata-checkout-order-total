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
	
	/** Limit on product count that can apply to this special. */
	protected Integer limit;
	
	protected Special(String productName, BigDecimal productPrice, int numProductsRequired, int numDiscounted) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.numProductPer = numProductsRequired;
		this.numDiscountedPer = numDiscounted;
	}
	
	/**
	 * Method to retrieve the discounted price for a product and type of special
	 * 
	 * @return BigDecimal original product price - discount value of special
	 */
	public abstract BigDecimal determineDiscountPriceDifference();
	
	/**
	 * Given a product count that applies to special, determine the number of times the
	 * discount would apply.
	 * 
	 * @return number of discounted products in a given transaction special
	 */
	public abstract BigDecimal determineDiscountProductCount(int productCount);
	
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
			int specialProductCount = productCounts.get(this.productName);
			int numberOfProductRequiredToGetDiscount = this.numProductPer;
			//if we have enough of that item to fulfill special requirements
			if(numberOfProductRequiredToGetDiscount < specialProductCount) {
				
				//subtract discounted price difference * number that should have been discounted from price total
				BigDecimal dicountPriceDifference = this.determineDiscountPriceDifference();
				BigDecimal countProductsDiscounted = this.determineDiscountProductCount(specialProductCount);
				preTaxTotal = preTaxTotal.subtract(dicountPriceDifference.multiply(countProductsDiscounted));
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
	
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
}
