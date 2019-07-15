package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * The PercentOffSpecial model represents the variables for the equation
 * 'Buy N get M %X off" for a specific Product. 
 * 
 * @author Derek Messer
 *
 */
public class PercentOffSpecial extends Special {
	
	/** Decimal value between [0, 1] representing percent off discount*/
	private BigDecimal percentOffAsDecimal;
	
	public PercentOffSpecial(String productName, BigDecimal productPrice, int numProductsRequired, int numDiscounted, BigDecimal percentOff) {
		super(productName, productPrice, numProductsRequired, numDiscounted);
		this.percentOffAsDecimal = percentOff;
	}

	@Override
	public BigDecimal determineDiscountPriceDifference() {
		return this.productPrice.multiply(this.percentOffAsDecimal);
	}

}
