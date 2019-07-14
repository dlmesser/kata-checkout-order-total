package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * Product class to represent items that can be scanned.
 * @author Derek Messer
 */
public class Product {
	String name;
	BigDecimal price;
	
	public Product() {
	}
	
	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
