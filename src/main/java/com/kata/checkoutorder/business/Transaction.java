package com.kata.checkoutorder.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kata.checkoutorder.model.Product;

public class Transaction {

	List<Product> products = new ArrayList<Product>();
	BigDecimal total;
	
	public Transaction() {
		products = new ArrayList<Product>();
		total = new BigDecimal(0);
	}
	
	public void scanProduct(Product item) {
		products.add(item);
		total = updateTotal();
	}

	public List<Product> getProducts() {
		return products;
	}

	public BigDecimal updateTotal() {
		BigDecimal preTaxTotal = new BigDecimal(0);
		
		for(Product item : products) {
			preTaxTotal = preTaxTotal.add(item.getPrice());
		}
		
		return preTaxTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
