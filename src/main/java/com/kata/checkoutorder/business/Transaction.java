package com.kata.checkoutorder.business;

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

}
