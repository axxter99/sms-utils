package org.sakaiproject.sms.hibernate.test;

import java.util.Date;
import java.util.List;

import org.sakaiproject.sms.hibernate.bean.SearchFilterBean;
import org.sakaiproject.sms.hibernate.bean.SearchResultContainer;
import org.sakaiproject.sms.hibernate.logic.impl.HibernateLogicFactory;
import org.sakaiproject.sms.hibernate.logic.impl.exception.SmsAccountNotFoundException;
import org.sakaiproject.sms.hibernate.model.SmsAccount;
import org.sakaiproject.sms.hibernate.model.SmsTransaction;
import org.sakaiproject.sms.hibernate.model.constants.SmsHibernateConstants;
import org.sakaiproject.sms.hibernate.util.AbstractBaseTestCase;
import org.sakaiproject.sms.hibernate.util.HibernateUtil;

/**
 * The Class SmsTransactionTest.
 */
public class SmsTransactionTest extends AbstractBaseTestCase {

	static {
		HibernateUtil.createSchema();
	}

	/**
	 * Instantiates a new sms transaction test.
	 */
	public SmsTransactionTest() {
	}

	/**
	 * Instantiates a new sms transaction test.
	 * 
	 * @param name
	 *            the name
	 */
	public SmsTransactionTest(String name) {
		super(name);
	}

	/**
	 * Test get sms transaction by id.
	 */
	public void testGetSmsTransactionById() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("0");
		smsAccount.setSakaiSiteId("0");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(0f);
		smsAccount.setAccountName("accountname");
		smsAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setBalance(1.32f);
		smsTransaction.setSakaiUserId("sakaiUserId");
		smsTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
		smsTransaction.setTransactionTypeCode("TC");
		smsTransaction.setTransactionCredits(666);
		smsTransaction.setTransactionAmount(1000.00f);

		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSmsTaskId(1L);

		HibernateLogicFactory.getTransactionLogic().insertCreditTransaction(
				smsTransaction);

		SmsTransaction getSmsTransaction = HibernateLogicFactory
				.getTransactionLogic()
				.getSmsTransaction(smsTransaction.getId());
		assertTrue("Object not persisted", smsTransaction.exists());
		assertNotNull(getSmsTransaction);
		assertEquals(smsTransaction, getSmsTransaction);
	}

	/**
	 * Test insert debit transaction.
	 */
	public void testInsertDebitTransaction() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("1");
		smsAccount.setSakaiSiteId("1");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(0f);
		smsAccount.setAccountName("accountname");
		smsAccount.setAccountEnabled(true);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setBalance(1.32f);
		smsTransaction.setSakaiUserId("sakaiUserId");
		smsTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
		smsTransaction.setTransactionTypeCode("TC");
		smsTransaction.setTransactionCredits(666);
		smsTransaction.setTransactionAmount(1000.00f);

		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSmsTaskId(1L);

		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);
		HibernateLogicFactory.getTransactionLogic().insertDebitTransaction(
				smsTransaction);
		assertTrue("Object not persisted", smsTransaction.exists());

		// Check the record was created on the DB... an id will be assigned.
		SmsAccount theNewAccount = HibernateLogicFactory.getAccountLogic()
				.getSmsAccount(smsAccount.getId());
		assertNotNull(theNewAccount);
		// Check updated account balance with positive value
		assertTrue(theNewAccount.getBalance() == 1000f);
	}

	/**
	 * Test insert credit transaction.
	 */
	public void testInsertCreditTransaction() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("2");
		smsAccount.setSakaiSiteId("2");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(0f);
		smsAccount.setAccountName("accountname");
		smsAccount.setAccountEnabled(true);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setBalance(1.32f);
		smsTransaction.setSakaiUserId("sakaiUserId");
		smsTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
		smsTransaction.setTransactionTypeCode("TC");
		smsTransaction.setTransactionCredits(666);
		smsTransaction.setTransactionAmount(1000.00f);

		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSmsTaskId(1L);

		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);
		HibernateLogicFactory.getTransactionLogic().insertCreditTransaction(
				smsTransaction);
		assertTrue("Object not persisted", smsTransaction.exists());

		// Check the record was created on the DB... an id will be assigned.
		SmsAccount theNewAccount = HibernateLogicFactory.getAccountLogic()
				.getSmsAccount(smsAccount.getId());
		assertNotNull(theNewAccount);
		// Check updated account balance with negative value
		assertTrue(theNewAccount.getBalance() == -1000f);
	}

	/**
	 * Test get sms transactions.
	 */
	public void testGetSmsTransactions() {
		List<SmsTransaction> transactions = HibernateLogicFactory
				.getTransactionLogic().getAllSmsTransactions();
		assertNotNull("Returnend collection is null", transactions);
		assertTrue("No records returned", transactions.size() > 0);
	}

	/**
	 * Tests the getMessagesForCriteria method
	 */
	public void testGetTransactionsForCriteria() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("3");
		smsAccount.setSakaiSiteId("3");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(5000.00f);
		smsAccount.setAccountName("accountName");
		smsAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setBalance(1.32f);
		smsTransaction.setSakaiUserId("sakaiUserId");
		smsTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
		smsTransaction.setTransactionTypeCode("TTC");
		smsTransaction.setTransactionCredits(666);
		smsTransaction.setTransactionAmount(1000.00f);
		smsTransaction.setSmsTaskId(1L);

		smsTransaction.setSmsAccount(smsAccount);

		HibernateLogicFactory.getTransactionLogic().insertCreditTransaction(
				smsTransaction);

		try {

			assertTrue("Object not created successfully", smsTransaction
					.exists());

			SearchFilterBean bean = new SearchFilterBean();
			bean.setTransactionType(smsTransaction.getTransactionTypeCode());
			bean.setNumber(smsTransaction.getSmsAccount().getId().toString());
			bean.setDateFrom(new Date());
			bean.setDateTo(new Date());
			bean.setSender(smsTransaction.getSakaiUserId());

			List<SmsTransaction> transactions = HibernateLogicFactory
					.getTransactionLogic().getPagedSmsTransactionsForCriteria(
							bean).getPageResults();
			assertTrue("Collection returned has no objects", transactions
					.size() > 0);

			for (SmsTransaction transaction : transactions) {
				// We know that only one transaction should be returned
				assertEquals(transaction, smsTransaction);
			}
		} catch (Exception se) {
			fail(se.getMessage());
		}
		SmsAccount account = HibernateLogicFactory.getAccountLogic()
				.getSmsAccount(smsAccount.getId());
		HibernateLogicFactory.getAccountLogic().deleteSmsAccount(account);
	}

	/**
	 * Test get tasks for criteria_ paging.
	 */
	public void testGetTasksForCriteria_Paging() {

		int recordsToInsert = 93;

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("4");
		smsAccount.setSakaiSiteId("4");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(5000.00f);
		smsAccount.setAccountName("accountname");
		smsAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);

		for (int i = 0; i < recordsToInsert; i++) {

			SmsTransaction smsTransaction = new SmsTransaction();
			smsTransaction.setBalance(1.32f);
			smsTransaction.setSakaiUserId("sakaiUserId");
			smsTransaction.setTransactionDate(new Date(System
					.currentTimeMillis()));
			smsTransaction.setTransactionTypeCode("TC");
			smsTransaction.setTransactionCredits(i);
			smsTransaction.setTransactionAmount(1000.00f);
			smsTransaction.setSmsAccount(smsAccount);
			smsTransaction.setSmsTaskId(1L);

			HibernateLogicFactory.getTransactionLogic()
					.insertCreditTransaction(smsTransaction);
		}
		try {

			SearchFilterBean bean = new SearchFilterBean();
			bean.setNumber(smsAccount.getId().toString());
			bean.setDateFrom(new Date());
			bean.setDateTo(new Date());
			bean.setTransactionType("TC");
			bean.setSender("sakaiUserId");

			bean.setCurrentPage(2);

			SearchResultContainer<SmsTransaction> con = HibernateLogicFactory
					.getTransactionLogic().getPagedSmsTransactionsForCriteria(
							bean);
			List<SmsTransaction> tasks = con.getPageResults();
			assertTrue("Incorrect collection size returned",
					tasks.size() == SmsHibernateConstants.DEFAULT_PAGE_SIZE);

			// Test last page. We know there are 124 records to this should
			// return a list of 4
			int pages = recordsToInsert
					/ SmsHibernateConstants.DEFAULT_PAGE_SIZE;
			// set to last page
			if (recordsToInsert % SmsHibernateConstants.DEFAULT_PAGE_SIZE == 0) {
				bean.setCurrentPage(pages);
			} else {
				bean.setCurrentPage(pages + 1);
			}

			con = HibernateLogicFactory.getTransactionLogic()
					.getPagedSmsTransactionsForCriteria(bean);
			tasks = con.getPageResults();
			// int lastPageRecordCount = recordsToInsert % pages;
			int lastPageRecordCount = recordsToInsert
					- (pages * SmsHibernateConstants.DEFAULT_PAGE_SIZE);
			assertTrue("Incorrect collection size returned",
					tasks.size() == lastPageRecordCount);

		} catch (Exception se) {
			fail(se.getMessage());
		}
	}

	public void testCreateReserveCreditsTransactionNoAccountFound()
			throws Exception {

		try {
			HibernateLogicFactory.getTransactionLogic().reserveCredits(123L,
					123L, 110);
			fail("Insert should fail since there is no account with id 123");
		} catch (SmsAccountNotFoundException expected) {
		} catch (Exception notExpected) {
			fail("An account not found exception should be thrown");
		}
	}

	public void testCreateReserveCreditsTask() {

		SmsAccount testAccount = new SmsAccount();
		testAccount.setSakaiUserId("5");
		testAccount.setSakaiSiteId("5");
		testAccount.setMessageTypeCode("12345");
		testAccount.setOverdraftLimit(10000.00f);
		testAccount.setBalance(5000.00f);
		testAccount.setAccountName("accountName");
		testAccount.setAccountEnabled(true);
		testAccount.setBalance(1000f);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(testAccount);

		try {
			HibernateLogicFactory.getTransactionLogic().reserveCredits(100L,
					testAccount.getId(), 110);
		} catch (Exception notExpected) {
			fail("Transaction should save successfully" + notExpected);
		}
	}

	public void testCreateCancelTransaction() throws Exception {

		SmsAccount testAccount = new SmsAccount();
		testAccount.setSakaiUserId("3");
		testAccount.setSakaiSiteId("3");
		testAccount.setMessageTypeCode("12345");
		testAccount.setOverdraftLimit(10000.00f);
		testAccount.setBalance(5000.00f);
		testAccount.setAccountName("accountName");
		testAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(testAccount);

		try {
			HibernateLogicFactory.getTransactionLogic().cancelTransaction(101L,
					testAccount.getId());
		} catch (Exception notExpected) {
			fail("Transaction should save successfully" + notExpected);
		}

	}

	/**
	 * Test delete sms transaction.
	 */
	public void testDeleteSmsTransaction() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("6");
		smsAccount.setSakaiSiteId("6");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(5000.00f);
		smsAccount.setAccountName("accountName");
		smsAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setBalance(1.32f);
		smsTransaction.setSakaiUserId("sakaiUserId");
		smsTransaction.setTransactionDate(new Date(System.currentTimeMillis()));
		smsTransaction.setTransactionTypeCode("TTC");
		smsTransaction.setTransactionCredits(666);
		smsTransaction.setTransactionAmount(1000.00f);
		smsTransaction.setSmsTaskId(1L);

		smsTransaction.setSmsAccount(smsAccount);

		HibernateLogicFactory.getTransactionLogic().insertCreditTransaction(
				smsTransaction);

		HibernateLogicFactory.getTransactionLogic().deleteSmsTransaction(
				smsTransaction);
		SmsTransaction getSmsTransaction = HibernateLogicFactory
				.getTransactionLogic()
				.getSmsTransaction(smsTransaction.getId());
		assertNull(getSmsTransaction);
		assertNull("Object not removed", getSmsTransaction);
	}

	public void testGetSmsTransactionsForAccountId() {

		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId("7");
		smsAccount.setSakaiSiteId("7");
		smsAccount.setMessageTypeCode("12345");
		smsAccount.setOverdraftLimit(10000.00f);
		smsAccount.setBalance(5000.00f);
		smsAccount.setAccountName("accountName");
		smsAccount.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(smsAccount);

		int noOfTrans = 4;
		for (int i = 0; i < noOfTrans; i++) {
			SmsTransaction smsTransaction = new SmsTransaction();
			smsTransaction.setBalance(1.32f);
			smsTransaction.setSakaiUserId("1" + i);
			smsTransaction.setTransactionDate(new Date(System
					.currentTimeMillis()));
			smsTransaction.setTransactionTypeCode("TC");
			smsTransaction.setTransactionCredits(i);
			smsTransaction.setTransactionAmount(1000.00f);
			smsTransaction.setSmsAccount(smsAccount);
			smsTransaction.setSmsTaskId(1L);
			HibernateLogicFactory.getTransactionLogic()
					.insertCreditTransaction(smsTransaction);
		}

		// Check it was crested
		assertTrue(smsAccount.exists());
		List<SmsTransaction> transactions = HibernateLogicFactory
				.getTransactionLogic().getSmsTransactionsForAccountId(
						smsAccount.getId());
		assertNotNull(transactions);
		assertTrue(transactions.size() == noOfTrans);
		for (SmsTransaction smsTransaction : transactions) {
			assertNotNull(smsTransaction.getSmsAccount());
			assertTrue(smsTransaction.getSmsAccount().getId().equals(
					smsAccount.getId()));
		}

	}

}
