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

	@Override
	public BigDecimal determineDiscountProductCount(int productCount) {
		limit = this.hasProductLimit ? limit : productCount;
		
		int currentItemsDiscounted = 0;
		int currentItemsFullPrice = 0;
		for (int i = 0; i < productCount; i++) {
			
			//for every N at full price, discount M
			if (currentItemsFullPrice != 0 && currentItemsFullPrice % this.numProductPer == 0) {
				for(int j = 0; j < this.numDiscountedPer; j++) {
					//make sure to not mark more discounted than exist....
					
					if (i < productCount) {
						currentItemsDiscounted++;
						i++;
					}
				}
			}
			
			currentItemsFullPrice++;
		}
		return new BigDecimal(currentItemsDiscounted);
	}

}
