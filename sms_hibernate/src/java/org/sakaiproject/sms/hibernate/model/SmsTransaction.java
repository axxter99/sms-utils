/***********************************************************************************
 * SmsTransaction.java
 * Copyright (c) 2008 Sakai Project/Sakai Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.sms.hibernate.model;

import java.sql.Timestamp;

/**
 * Transactions for a specific sms account.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 19-Nov-2008
 */
public class SmsTransaction extends AbstractBaseModel {

	/** The balance. */
	private Float balance;

	/** The sakai user id. */
	private String sakaiUserId;

	/** The sms account id. */
	private Integer smsAccountId;

	/** The transaction amount. */
	private Float transactionAmount;

	/** The transaction credits. */
	private Integer transactionCredits;

	/** The transaction date. */
	private Timestamp transactionDate;

	/** The transaction type code. */
	private String transactionTypeCode;

	/**
	 * Instantiates a new sms transaction.
	 */
	public SmsTransaction() {

	}

	/**
	 * Gets the balance.
	 * 
	 * @return the balance
	 */
	public Float getBalance() {
		return balance;
	}

	/**
	 * Gets the sakai user id.
	 * 
	 * @return the sakai user id
	 */
	public String getSakaiUserId() {
		return sakaiUserId;
	}

	/**
	 * Gets the sms account id.
	 * 
	 * @return the sms account id
	 */
	public Integer getSmsAccountId() {
		return smsAccountId;
	}

	/**
	 * Gets the transaction amount.
	 * 
	 * @return the transaction amount
	 */
	public Float getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * Gets the transaction credits.
	 * 
	 * @return the transaction credits
	 */
	public Integer getTransactionCredits() {
		return transactionCredits;
	}

	/**
	 * Gets the transaction date.
	 * 
	 * @return the transaction date
	 */
	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	/**
	 * Gets the transaction type code.
	 * 
	 * @return the transaction type code
	 */
	public String getTransactionTypeCode() {
		return transactionTypeCode;
	}

	/**
	 * Sets the balance.
	 * 
	 * @param balance
	 *            the new balance
	 */
	public void setBalance(Float balance) {
		this.balance = balance;
	}

	/**
	 * Sets the sakai user id.
	 * 
	 * @param sakaiUserId
	 *            the new sakai user id
	 */
	public void setSakaiUserId(String sakaiUserId) {
		this.sakaiUserId = sakaiUserId;
	}

	/**
	 * Sets the sms account id.
	 * 
	 * @param smsAccountId
	 *            the new sms account id
	 */
	public void setSmsAccountId(Integer smsAccountId) {
		this.smsAccountId = smsAccountId;
	}

	/**
	 * Sets the transaction amount.
	 * 
	 * @param transactionAmount
	 *            the new transaction amount
	 */
	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * Sets the transaction credits.
	 * 
	 * @param transactionCredits
	 *            the new transaction credits
	 */
	public void setTransactionCredits(Integer transactionCredits) {
		this.transactionCredits = transactionCredits;
	}

	/**
	 * Sets the transaction date.
	 * 
	 * @param transactionDate
	 *            the new transaction date
	 */
	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * Sets the transaction type code.
	 * 
	 * @param transactionTypeCode
	 *            the new transaction type code
	 */
	public void setTransactionTypeCode(String transactionTypeCode) {
		this.transactionTypeCode = transactionTypeCode;
	}
}