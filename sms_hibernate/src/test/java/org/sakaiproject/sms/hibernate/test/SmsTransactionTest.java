package org.sakaiproject.sms.hibernate.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.sakaiproject.sms.hibernate.bean.SearchFilterBean;
import org.sakaiproject.sms.hibernate.logic.impl.SmsTransactionLogicImpl;
import org.sakaiproject.sms.hibernate.logic.impl.exception.SmsSearchException;
import org.sakaiproject.sms.hibernate.model.SmsTransaction;
import org.sakaiproject.sms.hibernate.util.DateUtil;

public class SmsTransactionTest extends TestCase {

	private static SmsTransactionLogicImpl logic = null;
	private static SmsTransaction insertSmsTransaction;

	static {
		logic = new SmsTransactionLogicImpl();

		insertSmsTransaction = new SmsTransaction();
		insertSmsTransaction.setBalance(1.32f);
		insertSmsTransaction.setSakaiUserId("sakaiUserId");
		insertSmsTransaction.setSmsAccountId(1);
		insertSmsTransaction.setTransactionDate(new Timestamp(System
				.currentTimeMillis()));
		insertSmsTransaction.setTransactionTypeCode("TC");
		insertSmsTransaction.setTransactionCredits(666);
		insertSmsTransaction.setTransactionAmount(1000.00f);
	}

	public SmsTransactionTest() {
	}

	public SmsTransactionTest(String name) {
		super(name);
	}

	public void testInsertSmsTransaction() {
		logic.persistSmsTransaction(insertSmsTransaction);
		// Check the record was created on the DB... an id will be assigned.
		assertTrue("Object not persisted", insertSmsTransaction.exists());
	}

	public void testGetSmsTransactionById() {
		SmsTransaction getSmsTransaction = logic
				.getSmsTransaction(insertSmsTransaction.getId());
		assertTrue("Object not persisted", insertSmsTransaction.exists());
		assertNotNull(getSmsTransaction);
		assertEquals(insertSmsTransaction, getSmsTransaction);
	}

	public void testUpdateSmsTransaction() {
		SmsTransaction smsTransaction = logic
				.getSmsTransaction(insertSmsTransaction.getId());
		smsTransaction.setSakaiUserId("newSakaiUserId");
		logic.persistSmsTransaction(smsTransaction);
		smsTransaction = logic.getSmsTransaction(insertSmsTransaction.getId());
		assertEquals("newSakaiUserId", smsTransaction.getSakaiUserId());
	}

	public void testGetSmsTransactions() {
		List<SmsTransaction> transactions = logic.getAllSmsTransactions();
		assertNotNull("Returnend collection is null", transactions);
		assertTrue("No records returned", transactions.size() > 0);
	}

	/**
	 * Tests the getMessagesForCriteria method
	 */
	public void testGetTransactionsForCriteria() {
		SmsTransaction insertSmsTransaction = new SmsTransaction();
		insertSmsTransaction.setBalance(1.32f);
		insertSmsTransaction.setSakaiUserId("sakaiUserId");
		insertSmsTransaction.setSmsAccountId(1);

		insertSmsTransaction.setTransactionDate(new Timestamp(System
				.currentTimeMillis()));
		insertSmsTransaction.setTransactionTypeCode("TTC");
		insertSmsTransaction.setTransactionCredits(666);
		insertSmsTransaction.setTransactionAmount(1000.00f);

		try {
			logic.persistSmsTransaction(insertSmsTransaction);
			assertTrue("Object not created successfullyu", insertSmsTransaction
					.exists());

			SearchFilterBean bean = new SearchFilterBean();
			bean.setTransactionType(insertSmsTransaction
					.getTransactionTypeCode());
			bean.setAccountNumber(insertSmsTransaction.getSmsAccountId());
			bean.setDateFrom(DateUtil.getDateString(new Date()));
			bean.setDateTo(DateUtil.getDateString(new Date()));
			bean.setSender(insertSmsTransaction.getSakaiUserId());

			List<SmsTransaction> transactions = logic
					.getSmsTransactionsForCriteria(bean);
			assertTrue("Collection returned has no objects", transactions
					.size() > 0);

			for (SmsTransaction transaction : transactions) {
				// We know that only one transaction should be returned
				assertEquals(transaction, insertSmsTransaction);
			}
		} catch (SmsSearchException se) {
			fail(se.getMessage());
		} finally {
			logic.deleteSmsTransaction(insertSmsTransaction);
		}
	}

	public void testDeleteSmsTransaction() {
		logic.deleteSmsTransaction(insertSmsTransaction);
		SmsTransaction getSmsTransaction = logic
				.getSmsTransaction(insertSmsTransaction.getId());
		assertNull(getSmsTransaction);
		assertNull("Object not removed", getSmsTransaction);
	}

}