/**********************************************************************************
 * $URL: $
 * $Id: $
 ***********************************************************************************
 *
 * Copyright (c) 2006, 2007 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.sms.logic.smpp.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.sms.logic.external.ExternalLogic;
import org.sakaiproject.sms.logic.hibernate.HibernateLogicLocator;
import org.sakaiproject.sms.logic.hibernate.exception.SmsAccountNotFoundException;
import org.sakaiproject.sms.logic.smpp.SmsBilling;
import org.sakaiproject.sms.model.hibernate.SmsAccount;
import org.sakaiproject.sms.model.hibernate.SmsConfig;
import org.sakaiproject.sms.model.hibernate.SmsMessage;
import org.sakaiproject.sms.model.hibernate.SmsTask;
import org.sakaiproject.sms.model.hibernate.SmsTransaction;
import org.sakaiproject.sms.model.hibernate.constants.SmsConstants;

// TODO: Auto-generated Javadoc
/**
 * The billing service will handle all financial functions for the sms tool in
 * Sakai.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 12-Dec-2008
 */
public class SmsBillingImpl implements SmsBilling {

	// Transaction Type properties
	private final static Properties PROPERTIES = new Properties();

	private final static Log LOG = LogFactory.getLog(SmsBillingImpl.class);

	private HibernateLogicLocator hibernateLogicLocator;

	public HibernateLogicLocator getHibernateLogicLocator() {
		return hibernateLogicLocator;
	}

	private ExternalLogic externalLogic;

	public void setExternalLogic(ExternalLogic externalLogic) {
		this.externalLogic = externalLogic;
	}

	public void setHibernateLogicLocator(
			HibernateLogicLocator hibernateLogicLocator) {
		this.hibernateLogicLocator = hibernateLogicLocator;
	}

	public void init() {
		try {
			final InputStream inputStream = this.getClass()
					.getResourceAsStream("/transaction_codes.properties");
			if (inputStream == null) {
				final FileInputStream fileInputStream = new FileInputStream(
						"transaction_codes.properties");

				PROPERTIES.load(fileInputStream);
				if (fileInputStream != null) {
					fileInputStream.close();
				}

			} else {
				PROPERTIES.load(inputStream);
			}
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Credits an account by the supplied amount of credits.
	 * 
	 * @param accountId
	 * @param creditsToDebit
	 */
	public synchronized void creditAccount(final Long accountId,
			final long creditsToDebit, String description) {

		final SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(accountId);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setTransactionCredits(Long.valueOf(creditsToDebit)
				.intValue());
		smsTransaction.setCreditBalance(creditsToDebit);
		smsTransaction.setSakaiUserId(account.getSakaiUserId());
		smsTransaction.setSmsAccount(account);
		smsTransaction.setSmsTaskId(0L);
		smsTransaction.setSakaiUserId(hibernateLogicLocator.getExternalLogic()
				.getCurrentUserId());
		smsTransaction.setDescription(description);
		
		hibernateLogicLocator.getSmsTransactionLogic()
				.insertCreditAccountTransaction(smsTransaction);

		String txRef = "/sms-account/" + account.getId() + "/transaction/"
				+ smsTransaction.getId();
		externalLogic.postEvent(ExternalLogic.SMS_EVENT_ACCOUNT_CREDIT, txRef,
				null);
	}

	/**
	 * Add extra credits to the specific account by making an entry into
	 * SMS_TRANSACTION Also update the available credits on the account.
	 * 
	 * @param accountID
	 *            the account id
	 * @param creditCount
	 *            the credit count
	 * @deprecated Not implemented
	 */
	public synchronized void allocateCredits(Long accountID, int creditCount) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * Return true of the account has the required credits available. Take into
	 * account overdraft limits, if applicable.
	 * 
	 * @param smsTask
	 * @parm overDraftCheck
	 * @return
	 */
	public synchronized boolean checkSufficientCredits(SmsTask smsTask,
			boolean overDraftCheck) {
		return this.checkSufficientCredits(smsTask.getSmsAccountId(), smsTask
				.getCreditEstimate(), overDraftCheck);
	}

	/**
	 * 
	 * Return true of the account has the required credits available. Take into
	 * account overdraft limits, if applicable.
	 * 
	 * @param smsTask
	 * @return
	 */
	public synchronized boolean checkSufficientCredits(SmsTask smsTask) {
		return this.checkSufficientCredits(smsTask.getSmsAccountId(), smsTask
				.getCreditEstimate());
	}

	/**
	 * Return true of the account has the required credits available.
	 * 
	 * @param accountID
	 *            the account id
	 * @param creditsRequired
	 *            the credits required
	 * 
	 * @return true, if sufficient credits
	 */
	public synchronized boolean checkSufficientCredits(Long accountID,
			Integer creditsRequired) {

		return this.checkSufficientCredits(accountID, creditsRequired, false);
	}

	/**
	 * Return true of the account has the required credits available.
	 * 
	 * @param accountID
	 *            the account id
	 * @param creditsRequired
	 *            the credits required
	 * @param overDraftCheck
	 *            the overDraftCheck
	 * 
	 * @return true, if sufficient credits
	 */
	public synchronized boolean checkSufficientCredits(Long accountID,
			Integer creditsRequired, boolean overDraftCheck) {
		final SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(accountID);

		// Account is null or disabled
		if (account == null
				|| !account.getAccountEnabled()
				|| creditsRequired == null
				|| creditsRequired < 0
				|| (account.getEnddate() != null && account.getEnddate()
						.before(new Date()))) {
			return false;
		}

		boolean sufficientCredit = false;

		Long avalibleCredits = account.getCredits();

		if (overDraftCheck && account.getOverdraftLimit() != null) {
			avalibleCredits += account.getOverdraftLimit();
		}

		if (avalibleCredits >= creditsRequired) {
			sufficientCredit = true;
		}

		return sufficientCredit;
	}

	/**
	 * Convert amount to credits.
	 * 
	 * @param amount
	 *            the amount
	 * 
	 * @return the double
	 */
	public Long convertAmountToCredits(Float amount) {
		final SmsConfig config = hibernateLogicLocator.getSmsConfigLogic()
				.getOrCreateSystemSmsConfig();
		final Float result = (amount / config.getCreditCost());
		return result.longValue();
	}

	/**
	 * Convert the given credits to currency base on the defined conversion
	 * value at the given time.
	 * 
	 * @param creditCount
	 *            the credit count
	 * 
	 * @return the credit amount
	 */
	public Float convertCreditsToAmount(long creditCount) {
		SmsConfig config = hibernateLogicLocator.getSmsConfigLogic()
				.getOrCreateSystemSmsConfig();
		return config.getCreditCost() * creditCount;
	}

	/**
	 * Return the currency amount available in the account.
	 * 
	 * @param accountID
	 *            the account id
	 * 
	 * @return the account balance
	 * @deprecated Not implemented
	 */
	public double getAccountBalance(Long accountID) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return credits available in the account.
	 * 
	 * @param accountID
	 *            the account id
	 * 
	 * @return the account credits
	 * @deprecated Not implemented
	 */
	public int getAccountCredits(Long accountID) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Use Sakai siteID, Sakai userID and account type to get a valid account
	 * id. AccountType is only outgoing masses for now.
	 * 
	 * @param sakaiSiteID
	 *            (e.g. !admin)
	 * @param sakaiUserID
	 *            the sakai user id
	 * 
	 * @return the account id
	 * 
	 * @throws SmsAccountNotFoundException
	 *             the sms account not found exception
	 */
	public Long getAccountID(String sakaiSiteID, String sakaiUserID)
			throws SmsAccountNotFoundException {
		SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(sakaiSiteID, sakaiUserID);
		if (account == null) {
			throw new SmsAccountNotFoundException();

		} else {
			return account.getId();

		}

	}

	/**
	 * Return a list of all transactions between startDate and endDate for the
	 * specific account.
	 * 
	 * @param accountID
	 *            the account id
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * 
	 * @return the acc transactions
	 * @deprecated Not implemented
	 */
	public Set getAccTransactions(Long accountID, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * Return all accounts linked to the given Sakai site.
	 * 
	 * @param sakaiSiteID
	 *            the sakai site id
	 * 
	 * @return the all site accounts
	 * @deprecated Not implemented
	 */
	public Set getAllSiteAccounts(String sakaiSiteID) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * Insert a new account and return the new account id.
	 * 
	 * @param sakaiSiteID
	 *            the sakai site id
	 * 
	 * @return true, if insert account
	 * @deprecated Not implemented
	 */
	public boolean insertAccount(String sakaiSiteID) {
		return false;
	}

	/**
	 * Insert a new transaction for the given account id.
	 * 
	 * @param accountID
	 *            the account id
	 * @param transCodeID
	 *            the trans code id
	 * @param creditAmount
	 * @return true, if insert transaction the credit amount
	 * @deprecated Not implemented
	 */
	public boolean insertTransaction(Long accountID, int transCodeID,
			int creditAmount) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Insert a new transaction and indicate that the credits are reserved. If
	 * the request is pending and the administrator delete the request, the
	 * reservation must be rolled back with another transaction.
	 * 
	 * @param smsTask
	 *            the sms task
	 * 
	 * @return true, if reserve credits
	 */
	public synchronized boolean reserveCredits(SmsTask smsTask) {

		SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(smsTask.getSmsAccountId());

		if (account == null) {
			// Account does not existaccount.getCredits() -
			return false;
		}

		if (smsTask.getMessageTypeId().equals(
				SmsConstants.MESSAGE_TYPE_MOBILE_ORIGINATING)) {
			if (!checkSufficientCredits(smsTask, true)) {
				return false;
			}
		} else {
			if (!checkSufficientCredits(smsTask, false)) {
				return false;
			}
		}

		SmsTransaction smsTransaction = new SmsTransaction();

		// Set transaction credit and Credits to negative number because we are
		// reserving.
		int credits = smsTask.getCreditEstimate() * -1;
		smsTransaction.setCreditBalance(Long.valueOf(credits));
		smsTransaction.setTransactionCredits(credits);
		smsTransaction.setSakaiUserId(smsTask.getSenderUserId());
		smsTransaction.setSmsAccount(account);
		smsTransaction.setSmsTaskId(smsTask.getId());

		// Insert credit transaction
		hibernateLogicLocator.getSmsTransactionLogic()
				.insertReserveTransaction(smsTransaction);
		return true;

	}

	/**
	 * Credits account for a message that came in late.
	 * 
	 * @param smsTask
	 * @return true, if successful
	 */
	public synchronized boolean debitLateMessage(SmsMessage smsMessage) {
		SmsTask smsTask = smsMessage.getSmsTask();
		SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(smsTask.getSmsAccountId());
		if (account == null) {
			// Account does not exist
			return false;
		}

		SmsTransaction smsTransaction = new SmsTransaction();

		// The juicy bits

		smsTransaction.setCreditBalance((-1L));
		smsTransaction.setTransactionCredits(-1);
		smsTransaction.setSakaiUserId(smsTask.getSenderUserId());
		smsTransaction.setSmsAccount(account);
		smsTransaction.setSmsTaskId(smsTask.getId());

		hibernateLogicLocator.getSmsTransactionLogic()
				.insertLateMessageTransaction(smsTransaction);

		return true;

	}

	/**
	 * Recalculate balance for a specific account.
	 * 
	 * @param accountId
	 *            the account id
	 * @param account
	 *            the account
	 */
	private synchronized void recalculateAccountBalance(Long accountId,
			SmsAccount account) {
		hibernateLogicLocator.getSmsAccountLogic().recalculateAccountBalance(
				accountId, account);
	}

	/**
	 * Recalculate balance for a specific account.
	 * 
	 * @param account
	 *            the account
	 */
	private synchronized void recalculateAccountBalance(SmsAccount account) {
		recalculateAccountBalance(null, account);
	}

	/**
	 * Recalculate balance for a specific account.
	 * 
	 * @param accountId
	 *            the account id
	 */
	public synchronized void recalculateAccountBalance(Long accountId) {
		recalculateAccountBalance(accountId, null);
	}

	/**
	 * Recalculate balances for all existing accounts.
	 */
	public synchronized void recalculateAccountBalances() {
		List<SmsAccount> accounts = hibernateLogicLocator.getSmsAccountLogic()
				.getAllSmsAccounts();
		for (SmsAccount account : accounts) {
			recalculateAccountBalance(account);
		}
	}

	/**
	 * Cancel pending request.
	 * 
	 * @param smsTaskId
	 *            the sms task id
	 * 
	 * @return true, if successful
	 */
	public synchronized boolean cancelPendingRequest(Long smsTaskId) {

		SmsTask smsTask = hibernateLogicLocator.getSmsTaskLogic().getSmsTask(
				smsTaskId);
		SmsAccount smsAccount = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(smsTask.getSmsAccountId());
		SmsTransaction origionalTransaction = hibernateLogicLocator
				.getSmsTransactionLogic().getCancelSmsTransactionForTask(
						smsTaskId);

		SmsTransaction smsTransaction = new SmsTransaction();

		if (origionalTransaction == null) {
			return false;
		}

		// The juicy bits
		int transactionCredits = origionalTransaction.getTransactionCredits()
				* -1;// Reverse the sign cause we are deducting from the account
		smsTransaction.setTransactionCredits(transactionCredits);
		smsTransaction.setCreditBalance(Long.valueOf(transactionCredits));

		smsTransaction.setSakaiUserId(smsTask.getSenderUserId());
		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSmsTaskId(smsTask.getId());

		hibernateLogicLocator.getSmsTransactionLogic()
				.insertCancelPendingRequestTransaction(smsTransaction);

		return true;
	}

	/**
	 * Settle credit difference. The group size might have change since the time
	 * that the task was requested. So we need to calculate the difference and
	 * settle the account.
	 * 
	 * @param smsTask
	 *            the sms task
	 * 
	 * @return true, if successful
	 */
	public boolean settleCreditDifference(SmsTask smsTask) {
		// we might want to use a separate account to pay when the overdraft is
		// exceeded.
		SmsAccount account = hibernateLogicLocator.getSmsAccountLogic()
				.getSmsAccount(smsTask.getSmsAccountId());
		if (account == null) {
			// Account does not exist
			return false;
		}

		SmsTransaction smsTransaction = new SmsTransaction();

		// The juicy bits
		int creditEstimate = smsTask.getCreditEstimateInt();
		int actualCreditsUsed = smsTask.getMessagesDelivered();
		int transactionCredits = creditEstimate - actualCreditsUsed;
		smsTransaction.setCreditBalance(Long.valueOf(transactionCredits));
		smsTransaction.setTransactionCredits(transactionCredits);

		smsTransaction.setSakaiUserId(smsTask.getSenderUserId());
		smsTransaction.setSmsAccount(account);
		smsTransaction.setSmsTaskId(smsTask.getId());

		hibernateLogicLocator.getSmsTransactionLogic().insertSettleTransaction(
				smsTransaction);

		return true;
	}

	public String getCancelCode() {
		return StringUtils.left(PROPERTIES.getProperty("TRANS_CANCEL", "TCAN"),
				5);
	}

	public String getCancelReserveCode() {
		return StringUtils.left(PROPERTIES.getProperty("TRANS_CANCEL_RESERVE",
				"RCAN"), 5);
	}

	public String getCreditAccountCode() {
		return StringUtils.left(PROPERTIES.getProperty("TRANS_CREDIT_ACCOUNT",
				"CRED"), 5);
	}

	public String getDebitLateMessageCode() {
		return StringUtils.left(PROPERTIES.getProperty(
				"TRANS_DEBIT_LATE_MESSAGE", "LATE"), 5);
	}

	public String getReserveCreditsCode() {
		return StringUtils.left(PROPERTIES.getProperty("TRANS_RESERVE_CREDITS",
				"RES"), 5);
	}

	public String getSettleDifferenceCode() {
		return StringUtils.left(PROPERTIES.getProperty(
				"TRANS_SETTLE_DIFFERENCE", "RSET"), 5);
	}

}
