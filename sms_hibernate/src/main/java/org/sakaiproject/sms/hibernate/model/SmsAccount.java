/***********************************************************************************
 * SmsAccount.java
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

/**
 * Each Sakai site will have its own set of accounts for billing outgoing
 * messages. A specific user may also have a account.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 19-Nov-2008
 */
public class SmsAccount extends BaseModel {

	/** The current account balance in currency. */
	private Float balance;

	/**
	 * The message type, will be incoming (MO) or outgoing (SO), currently only
	 * SO.
	 */
	private String messageTypeCode;

	/** Some accounts will be allowed to have a overdraft limit. */
	private Float overdraftLimit;

	/** The account must be linked to either an Sakai site or a Sakai user. */
	private String sakaiSiteId;

	/** The account must be linked to either an Sakai site or a Sakai user. */
	private String sakaiUserId;

	/**
	 * Instantiates a new sms account.
	 */
	public SmsAccount() {
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
	 * Gets the message type code.
	 * 
	 * @return the message type code
	 */
	public String getMessageTypeCode() {
		return messageTypeCode;
	}

	/**
	 * Gets the overdraft limit.
	 * 
	 * @return the overdraft limit
	 */
	public Float getOverdraftLimit() {
		return overdraftLimit;
	}

	/**
	 * Gets the sakai site id.
	 * 
	 * @return the sakai site id
	 */
	public String getSakaiSiteId() {
		return sakaiSiteId;
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
	 * Sets the balance.
	 * 
	 * @param balance
	 *            the new balance
	 */
	public void setBalance(Float balance) {
		this.balance = balance;
	}

	/**
	 * Sets the message type code.
	 * 
	 * @param messageTypeCode
	 *            the new message type code
	 */
	public void setMessageTypeCode(String messageTypeCode) {
		this.messageTypeCode = messageTypeCode;
	}

	/**
	 * Sets the overdraft limit.
	 * 
	 * @param overdraftLimit
	 *            the new overdraft limit
	 */
	public void setOverdraftLimit(Float overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}

	/**
	 * Sets the sakai site id.
	 * 
	 * @param sakaiSiteId
	 *            the new sakai site id
	 */
	public void setSakaiSiteId(String sakaiSiteId) {
		this.sakaiSiteId = sakaiSiteId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result
				+ ((messageTypeCode == null) ? 0 : messageTypeCode.hashCode());
		result = prime * result
				+ ((overdraftLimit == null) ? 0 : overdraftLimit.hashCode());
		result = prime * result
				+ ((sakaiSiteId == null) ? 0 : sakaiSiteId.hashCode());
		result = prime * result
				+ ((sakaiUserId == null) ? 0 : sakaiUserId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof SmsAccount))
			return false;
		SmsAccount other = (SmsAccount) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (messageTypeCode == null) {
			if (other.messageTypeCode != null)
				return false;
		} else if (!messageTypeCode.equals(other.messageTypeCode))
			return false;
		if (overdraftLimit == null) {
			if (other.overdraftLimit != null)
				return false;
		} else if (!overdraftLimit.equals(other.overdraftLimit))
			return false;
		if (sakaiSiteId == null) {
			if (other.sakaiSiteId != null)
				return false;
		} else if (!sakaiSiteId.equals(other.sakaiSiteId))
			return false;
		if (sakaiUserId == null) {
			if (other.sakaiUserId != null)
				return false;
		} else if (!sakaiUserId.equals(other.sakaiUserId))
			return false;
		return true;
	}

}