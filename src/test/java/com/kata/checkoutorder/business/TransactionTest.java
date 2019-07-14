package com.kata.checkoutorder.business;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kata.checkoutorder.business.Transaction;

public class TransactionTest {

	@Test
	public void testScanItem() {
		Transaction testTransaction = new Transaction();
		
		testTransaction.scanItem("soup");
		
		assertEquals(1, testTransaction.getItems().size());
		
	}

}
