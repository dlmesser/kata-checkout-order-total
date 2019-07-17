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

public class TransactionTest {
	
	private Transaction testTransaction;
	private Product perUnitProduct;
	private Product weightedProduct;
	
	private Special exactPriceDiscount;
	private Special percentDiscount; 
	
	@Before
	public void setup() {
		testTransaction = new Transaction();
		perUnitProduct = new Product("soup", new BigDecimal("1.00"));
		weightedProduct = new Product("bananas", new BigDecimal("1.50"));
		weightedProduct.setPounds(new BigDecimal("2"));
		
		int numItemsRequiredToFulfillSpecial = 3;
		int numDiscounted = 1;
		BigDecimal percentOff = new BigDecimal("1");
		percentDiscount = new PercentOffSpecial(perUnitProduct, numItemsRequiredToFulfillSpecial, numDiscounted, percentOff);
		
		BigDecimal discountPrice = new BigDecimal("5");
		exactPriceDiscount = new ExactPriceSpecial(perUnitProduct, numItemsRequiredToFulfillSpecial, discountPrice);
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
		
		assertEquals(new BigDecimal("1.00"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canAddMultipleProductsAndUpdateTotal() {
		testTransaction.scanProduct(perUnitProduct);
		testTransaction.scanProduct(weightedProduct);
		
		assertEquals(new BigDecimal("4.00"), testTransaction.getTotal());
		
	}
	
	@Test
	public void testScanItem_canAddMarkdownedProduct() {
		perUnitProduct.setMarkdown(new BigDecimal("0.50"));
		testTransaction.scanProduct(perUnitProduct);
		
		assertEquals(new BigDecimal("0.50"), testTransaction.getTotal());
		
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
		
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplySpecialAfterScanningItems() {
		//setup scenario to fulfill special (3 full price, 1 free)
		this.transactionSecnarioBuilder(perUnitProduct, 4);

		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplyPercentSpecialWithMultipleDiscountableProducts() {
		//add special to transaction
		testTransaction.addSpecial(percentDiscount);
		
		//setup scenario to fulfill special (6 full price, 2 free)
		this.transactionSecnarioBuilder(perUnitProduct, 8);
	
		assertEquals(new BigDecimal("6.00"), testTransaction.getTotal());
	}

	@Test
	public void testAddSpecial_canApplyPercentWithMultipleDiscountedPerFullPriceRequirement() {
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
		
		// 3 for 5 + 1.00 (P.S. - this is a terrible deal)
		assertEquals(new BigDecimal("6.00"), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplySpecialsWithLimits() {
		//setup scenario so special will apply once instead of twice without limit
		this.transactionSecnarioBuilder(perUnitProduct, 6);

		//add special to transaction
		exactPriceDiscount.setLimit(3);
		testTransaction.addSpecial(exactPriceDiscount);
		
		// 3 for 5 + (3 * 1) (P.S. - this is still a terrible deal)
		assertEquals(new BigDecimal("8").setScale(2), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplyPercentOffSpecialsWithLimits() {
		//setup scenario so special will apply once instead of twice without limit
		this.transactionSecnarioBuilder(perUnitProduct, 8);

		//add special to transaction
		percentDiscount.setLimit(4);
		testTransaction.addSpecial(percentDiscount);
		
		// 3 + 1 free + 4
		assertEquals(new BigDecimal("7").setScale(2), testTransaction.getTotal());
	}
	
	@Test
	public void testAddSpecial_canApplyPercentOffSpecialsWithWeightedProduct() {
		weightedProduct  = new Product("bananas", new BigDecimal("2.00"));
		weightedProduct.setPounds(new BigDecimal("6"));
		percentDiscount.setProduct(weightedProduct);
				
		testTransaction.scanProduct(weightedProduct);
		testTransaction.addSpecial(percentDiscount);
		
		// 3 pounds full price + 1 pound free + 2 pounds full price
		assertEquals(new BigDecimal("10").setScale(2), testTransaction.getTotal());
	}
	
	@Test
	public void testRemoveProduct_canRemoveProductAndUpdateTotal() {
		this.transactionSecnarioBuilder(perUnitProduct, 4);
		assertEquals(new BigDecimal("4.00"), testTransaction.getTotal());
		
		testTransaction.removeProduct(perUnitProduct);
		assertEquals(new BigDecimal("3.00"), testTransaction.getTotal());
		
	}
	

}
