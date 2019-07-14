package com.kata.checkoutorder.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.kata.checkoutorder.business.Transaction;
import com.kata.checkoutorder.model.Product;
import com.kata.checkoutorder.model.WeightedProduct;

public class TransactionTest {
	
	Transaction testTransaction;
	
	@Before
	public void setup() {
		testTransaction = new Transaction();
	}

	@Test
	public void testScanItem_canAddProduct() {
		Product item = new Product("soup", new BigDecimal("1.22"));
		testTransaction.scanProduct(item);
		
		assertEquals(1, testTransaction.getProducts().size());
		
	}
	
	@Test
	public void testScanWeightedItem_canAddWeightedProduct() {
		Product item = new WeightedProduct("bananas", new BigDecimal("1.50"), new BigDecimal("2"));
		testTransaction.scanProduct(item);
		
		assertEquals(1, testTransaction.getProducts().size());
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testCalculateTotal_canAddProductAndUpdateTotal() {
		Product item = new Product("soup", new BigDecimal("1.22"));
		testTransaction.scanProduct(item);
		
		assertEquals(new BigDecimal("1.22"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testCalculateTotal_canAddMultipleProductsAndUpdateTotal() {
		Product item = new Product("soup", new BigDecimal("1.22"));
		testTransaction.scanProduct(item);
		
		Product weightedItem = new WeightedProduct("bananas", new BigDecimal("1.50"), new BigDecimal("2"));
		testTransaction.scanProduct(weightedItem);
		
		assertEquals(new BigDecimal("4.22"), testTransaction.getTotal());
		
	}

}
