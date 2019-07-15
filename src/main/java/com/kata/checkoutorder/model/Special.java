package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * The Special model represents the variables for the equation
 * 'Buy N get M %X off" for a specific Product. 
 * 
 * @author Derek Messer
 *
 */
public class Special {
	String productName;
	BigDecimal productPrice;
	
	/** The variable N in 'Buy N get M %X'*/
	int numProductPer;
	
	/** The variable M in 'Buy N get M %X'*/
	int numDiscountedPer;
	
	/** Decimal value between [0, 1] representing percent off discount*/
	BigDecimal percentOffAsDecimal;

	public Special(String productName, BigDecimal productPrice, int numFullPriceProducts, int numDiscounted, BigDecimal percentOff) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.numProductPer = numFullPriceProducts;
		this.numDiscountedPer = numDiscounted;
		this.percentOffAsDecimal = percentOff;
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

	public BigDecimal getPercentOffAsDecimal() {
		return percentOffAsDecimal;
	}

	public void setPercentOffAsDecimal(BigDecimal percentOff) {
		this.percentOffAsDecimal = percentOff;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}
	
	
	

}
