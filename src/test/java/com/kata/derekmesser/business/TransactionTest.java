package com.kata.derekmesser.business;

import static org.junit.Assert.*;

import org.junit.Test;

public class TransactionTest {

	@Test
	public void testScanItem() {
		Transaction testTransaction = new Transaction();
		
		testTransaction.scanItem("soup");
		
		assertEquals(1, testTransaction.getItems().size());
		
	}

}
