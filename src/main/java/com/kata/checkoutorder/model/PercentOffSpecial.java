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
	
	public PercentOffSpecial(Product product, int numProductsRequired, int numDiscounted, BigDecimal percentOff) {
		super(product, numProductsRequired, numDiscounted);
		this.percentOffAsDecimal = percentOff;
	}

	@Override
	public BigDecimal determineDiscountPriceDifference() {
		if (this.product.getPounds() != null) {
			BigDecimal originalPricePerPound= (this.product.getPrice().divide(this.product.getPounds()));
			return originalPricePerPound.multiply(this.percentOffAsDecimal);
		}
		return this.product.getPrice().multiply(this.percentOffAsDecimal);
	}

	@Override
	public BigDecimal determineDiscountProductCount(int productCount) {
		//if this is a weighted product what we care about is total weight not count
		if (this.product.getPounds() != null) {
			productCount = productCount * this.product.getPounds().intValue();
		}
		
		limit = this.hasProductLimit ? limit : productCount;
		
		int currentItemsDiscounted = 0;
		int currentItemsFullPrice = 0;
		for (int i = 0; i < productCount; i++) {
			
			//for every N at full price, discount M
			if (currentItemsFullPrice != 0 && currentItemsFullPrice % this.numProductPer == 0) {
				for(int j = 0; j < this.numDiscountedPer; j++) {
					//make sure to not mark more discounted than exist....
					
					if (i < limit) {
						currentItemsDiscounted++;
						i++;
					}
				}
			}
			
			currentItemsFullPrice++;
		}
		return new BigDecimal(currentItemsDiscounted);
	}
	
	public BigDecimal determineDiscountProductWeight(int productWeight) {
		limit = this.hasProductLimit ? limit : productWeight;
		
		int currentItemsDiscounted = 0;
		int currentItemsFullPrice = 0;
		for (int i = 0; i < productWeight; i++) {
			
			//for every N at full price, discount M
			if (currentItemsFullPrice != 0 && currentItemsFullPrice % this.numProductPer == 0) {
				for(int j = 0; j < this.numDiscountedPer; j++) {
					//make sure to not mark more discounted than exist....
					
					if (i < limit) {
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
