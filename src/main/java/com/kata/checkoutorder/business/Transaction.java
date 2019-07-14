package com.kata.checkoutorder.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kata.checkoutorder.model.Product;

public class Transaction {

	List<Product> products = new ArrayList<Product>();
	
	public void scanProduct(Product item) {
		products.add(item);
		
	}

	public List<Product> getProducts() {
		return products;
	}

	public BigDecimal calculateTotal() {
		BigDecimal preTaxTotal = new BigDecimal(0);
		
		for(Product item : products) {
			preTaxTotal = preTaxTotal.add(item.getPrice());
		}
		
		return preTaxTotal;
	}

}
