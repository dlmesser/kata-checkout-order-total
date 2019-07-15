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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((markdown == null) ? 0 : markdown.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (markdown == null) {
			if (other.markdown != null)
				return false;
		} else if (!markdown.equals(other.markdown))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
	
}
