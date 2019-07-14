package com.kata.checkoutorder.business;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	List<String> items = new ArrayList<String>();
	
	public void scanItem(String item) {
		items.add(item);
		
	}

	public List<String> getItems() {
		return items;
	}

}
