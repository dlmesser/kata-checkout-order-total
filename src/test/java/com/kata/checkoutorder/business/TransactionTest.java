package com.kata.checkoutorder.business;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.kata.checkoutorder.business.Transaction;
import com.kata.checkoutorder.model.ExactPriceSpecial;
import com.kata.checkoutorder.model.Product;
import com.kata.checkoutorder.model.Special;
import com.kata.checkoutorder.model.PercentOffSpecial;
import com.kata.checkoutorder.model.WeightedProduct;

public class TransactionTest {
	
	private Transaction testTransaction;
	private Product perUnitProduct;
	private Product weightedProduct;
	
	private Special exactPriceDiscount;
	private Special percentDiscount; 
	
	@Before
	public void setup() {
		testTransaction = new Transaction();
		perUnitProduct = new Product("soup", new BigDecimal("1.22"));
		weightedProduct = new WeightedProduct("bananas", new BigDecimal("1.50"), new BigDecimal("2"));
		
		String itemName = "soup";
		BigDecimal itemPrice = perUnitProduct.getPrice();
		int numItemsRequiredToFulfillSpecial = 3;
		int numDiscounted = 1;
		BigDecimal percentOff = new BigDecimal("1");
		percentDiscount = new PercentOffSpecial(itemName, itemPrice, numItemsRequiredToFulfillSpecial, numDiscounted, percentOff);
		
		BigDecimal discountPrice = new BigDecimal("5");
		exactPriceDiscount = new ExactPriceSpecial(itemName, itemPrice, numItemsRequiredToFulfillSpecial, discountPrice);
	}
	
	private void transactionSecnarioBuilder(Product product, int numToScan) {
		for(int i = 0; i < numToScan; i++) {
			testTransaction.scanProduct(perUnitProduct);
		}
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
	public void testAddSpecial_canApplyPercentDiscountSpecialBuyThreeGetOneFree() {
		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		//setup scenario to fulfill special (3 full price, 1 free)
		this.transactionSecnarioBuilder(perUnitProduct, 4);
		
		assertEquals(new BigDecimal("3.66"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplySpecialAfterScanningItems() {
		//setup scenario to fulfill special (3 full price, 1 free)
		this.transactionSecnarioBuilder(perUnitProduct, 4);

		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		assertEquals(new BigDecimal("3.66"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplyPercentSpecialWithMultipleDiscountableProducts() {
		//Change prices for my sanity
		perUnitProduct.setPrice(new BigDecimal("1.00"));
		percentDiscount.setProductPrice(new BigDecimal("1.00"));
		
		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		//setup scenario to fulfill special (6 full price, 2 free)
		this.transactionSecnarioBuilder(perUnitProduct, 8);
	
		assertEquals(new BigDecimal("6.00"), testTransaction.getTotal());
	}

	@Test
	public void testAddSpecial_canApplyPercentWithMultipleDiscountedPerFullPriceRequirement() {
		//Change prices for my sanity
		perUnitProduct.setPrice(new BigDecimal("1.00"));
		percentDiscount.setProductPrice(new BigDecimal("1.00"));
		
		//update the M value to be more than 1
		percentDiscount.setNumDiscountedPer(2);
		
		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		//setup scenario to fulfill Buy 3 get 2 free special (3 full price, 2 free)
		this.transactionSecnarioBuilder(perUnitProduct, 3);
	
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
		
		//2 free
		this.transactionSecnarioBuilder(perUnitProduct, 2);
		
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
		
		//adding another is full price
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(new BigDecimal("4.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddExactPriceSpecial_canApplyExactPriceSpecial() {
		//setup scenario to fulfill special (3 for $5, 1 full price)
		this.transactionSecnarioBuilder(perUnitProduct, 4);

		//add special to transaction
		testTransaction.addSpecial(exactPriceDiscount);
		
		// 3 for 5 + 1.22 (P.S. - this is a terrible deal)
		assertEquals(new BigDecimal("6.22"), testTransaction.getTotal());
	}
	
	@Test
	public void testRemoveProduct_canRemoveProductAndUpdateTotal() {
		this.transactionSecnarioBuilder(perUnitProduct, 4);
		assertEquals(new BigDecimal("4.88"), testTransaction.getTotal());
		
		testTransaction.removeProduct(perUnitProduct);
		assertEquals(new BigDecimal("3.66"), testTransaction.getTotal());
		
	}
	

}
