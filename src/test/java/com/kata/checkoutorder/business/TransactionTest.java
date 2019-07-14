package com.kata.checkoutorder.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.kata.checkoutorder.business.Transaction;
import com.kata.checkoutorder.model.Product;

public class TransactionTest {

	@Test
	public void testScanItem() {
		Transaction testTransaction = new Transaction();
		
		Product item = new Product("soup", new BigDecimal("1.22"));
		testTransaction.scanProduct(item);
		
		assertEquals(1, testTransaction.getProducts().size());
		
	}

}
