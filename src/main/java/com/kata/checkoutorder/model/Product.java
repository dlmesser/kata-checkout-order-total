package com.kata.checkoutorder.model;

import java.math.BigDecimal;

/**
 * Product class to represent items that can be scanned.
 * @author Derek Messer
 */
public class Product {
	String name;
	BigDecimal price;
	BigDecimal markdown;
	
	public Product() {
	}
	
	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
		markdown = new BigDecimal(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price.subtract(markdown);
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMarkdown() {
		return markdown;
	}

	public void setMarkdown(BigDecimal markdown) {
		this.markdown = markdown;
	}
	
	
}
