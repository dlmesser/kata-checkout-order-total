package com.kata.checkoutorder.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.kata.checkoutorder.business.Transaction;
import com.kata.checkoutorder.model.Product;
import com.kata.checkoutorder.model.Special;
import com.kata.checkoutorder.model.WeightedProduct;

public class TransactionTest {
	
	Transaction testTransaction;
	Product perUnitProduct;
	Product weightedProduct;
	
	@Before
	public void setup() {
		testTransaction = new Transaction();
		perUnitProduct = new Product("soup", new BigDecimal("1.22"));
		weightedProduct = new WeightedProduct("bananas", new BigDecimal("1.50"), new BigDecimal("2"));
	}

	@Test
	public void testScanItem_canAddProduct() {
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(1, testTransaction.getProducts().size());
		
	}
	
	@Test
	public void testScanItem_canAddWeightedProduct() {
		testTransaction.scanProduct(weightedProduct);
		
		assertEquals(1, testTransaction.getProducts().size());
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testScanItem_canAddProductAndUpdateTotal() {
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(new BigDecimal("1.22"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canAddMultipleProductsAndUpdateTotal() {
		testTransaction.scanProduct(perUnitProduct);
		testTransaction.scanProduct(weightedProduct);
		
		assertEquals(new BigDecimal("4.22"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canAddMarkdownedProduct() {
		perUnitProduct.setMarkdown(new BigDecimal("0.50"));
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(new BigDecimal("0.72"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canAddMarkdownedWeightedProduct() {
		weightedProduct.setMarkdown(new BigDecimal("0.50"));
		testTransaction.scanProduct(weightedProduct);
		
		assertEquals(new BigDecimal("2.00"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canApplyPercentDiscountSpecialBuyThreeGetOneFree() {
		//Construct Special parameters
		String itemName = "soup";
		BigDecimal itemPrice = perUnitProduct.getPrice();
		int numItemsRequiredToFulfillSpecial = 3;
		int numDiscounted = 1;
		BigDecimal percentOff = new BigDecimal("1");
		Special percentDiscount = new Special(itemName, itemPrice, numItemsRequiredToFulfillSpecial, numDiscounted, percentOff);
		
		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		//setup scenario to fulfill special (3 full price, 1 free)
		testTransaction.scanProduct(perUnitProduct);
		testTransaction.scanProduct(perUnitProduct);
		testTransaction.scanProduct(perUnitProduct);
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(new BigDecimal("3.66"), testTransaction.getTotal());
	}

}
