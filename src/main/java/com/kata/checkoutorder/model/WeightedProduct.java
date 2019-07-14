package com.kata.checkoutorder.model;

import java.math.BigDecimal;

public class WeightedProduct extends Product {
	BigDecimal pounds;
	
	public WeightedProduct(String name, BigDecimal price, BigDecimal pounds) {
		super(name, price);
		this.pounds = pounds;
	}
	
	@Override
	public BigDecimal getPrice() {
		return price.multiply(pounds);
	}

}
