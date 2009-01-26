/***********************************************************************************
 * SmsAccountLogicImpl.java
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

package org.sakaiproject.sms.hibernate.logic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.sakaiproject.sms.hibernate.dao.SmsDao;
import org.sakaiproject.sms.hibernate.logic.SmsAccountLogic;
import org.sakaiproject.sms.hibernate.model.SmsAccount;
import org.sakaiproject.sms.hibernate.model.SmsTransaction;
import org.sakaiproject.sms.hibernate.model.constants.SmsPropertyConstants;
import org.sakaiproject.sms.hibernate.util.HibernateUtil;
import org.sakaiproject.sms.hibernate.util.SmsPropertyReader;

/**
 * The data service will handle all sms Account database transactions for the
 * sms tool in Sakai.
 * 
 * @author julian@psybergate.com
 * @version 1.0
 * @created 25-Nov-2008 08:12:41 AM
 */
public class SmsAccountLogicImpl extends SmsDao implements SmsAccountLogic {

	/**
	 * Leave this as protected to try and prevent the random instantiation of
	 * this class.
	 * <p>
	 * Use LogicFactory.java to get instances of logic classes.
	 */
	protected SmsAccountLogicImpl() {

	}

	/**
	 * Deletes and the given entity from the DB
	 */
	public void deleteSmsAccount(SmsAccount smsAccount) {
		delete(smsAccount);
	}

	/**
	 * Gets a SmsAccount entity for the given id
	 * 
	 * @param Long
	 *            sms account id
	 * @return sms congiguration
	 */
	public SmsAccount getSmsAccount(Long smsAccountId) {
		return (SmsAccount) findById(SmsAccount.class, smsAccountId);
	}

	/**
	 * Gets all the sms account records
	 * 
	 * @return List of SmsAccount objects
	 */
	public List<SmsAccount> getAllSmsAccounts() {
		List<SmsAccount> accounts = new ArrayList<SmsAccount>();
		Session s = HibernateUtil.getSession();
		Query query = s.createQuery("from SmsAccount");
		accounts = query.list();
		HibernateUtil.closeSession();
		return accounts;
	}

	/**
	 * This method will persists the given object.
	 * 
	 * If the object is a new entity then it will be created on the DB. If it is
	 * an existing entity then the record will be updates on the DB.
	 * 
	 * @param sms
	 *            account to be persisted
	 */
	public void persistSmsAccount(SmsAccount smsAccount) {
		persist(smsAccount);
	}

	/**
	 * Gets a SmsAccount entity for the given sakai site id or sakai user id.
	 * <p>
	 * If the property account.checkSiteIdBeforeUserId == true in sms.properties
	 * then the method will first attempt to find the sms account by sakai site
	 * id. If the returned account is null then it will attempt to finf it by
	 * the sakai user id. If no account is found null will be returned.
	 * <p>
	 * If the property account.checkSiteIdBeforeUserId == false then it will
	 * behave as described above but will first try find the account by sakai
	 * user id before the sakai site id.
	 * 
	 * @param sakaiSiteId
	 *            the sakai site id. Can be null.
	 * @param SakaiUserId
	 *            the sakai user id. Can be null.
	 * 
	 * @return sms congiguration
	 * 
	 */
	public SmsAccount getSmsAccount(String sakaiSiteId, String SakaiUserId) {
		boolean checkSiteBeforeUser = Boolean
				.parseBoolean(SmsPropertyReader
						.getProperty(SmsPropertyConstants.ACCOUNT_CHECK_SITE_ID_BEFORE_USER_ID));
		SmsAccount account = null;

		if (checkSiteBeforeUser) {
			account = getAccountBySakaiSiteId(sakaiSiteId);
			if (account == null) {
				return getAccountBySakaiUserId(SakaiUserId);
			} else {
				return account;
			}
		} else {
			account = getAccountBySakaiUserId(SakaiUserId);
			if (account == null) {
				return getAccountBySakaiSiteId(sakaiSiteId);
			} else {
				return account;
			}
		}
	}

	/**
	 * Gets the account by sakai site id.
	 * 
	 * @param sakaiSiteId
	 *            the sakai site id
	 * 
	 * @return the account by sakai site id
	 * 
	 * @throws MoreThanOneAccountFoundException
	 *             the more than one account found exception
	 */
	private SmsAccount getAccountBySakaiSiteId(String sakaiSiteId) {
		if (sakaiSiteId == null || sakaiSiteId.trim().equals("")) {
			return null;
		}
		SmsAccount account = null;
		StringBuilder hql = new StringBuilder();
		hql
				.append(" from SmsAccount account where (account.enddate is null or account.enddate > :today) ");
		hql.append(" and (account.sakaiSiteId = :sakaiSiteId)");
		List<SmsAccount> accounts = new ArrayList<SmsAccount>();
		Session s = HibernateUtil.getSession();
		Query query = s.createQuery(hql.toString());
		query.setDate("today", new Date());
		query.setParameter("sakaiSiteId", sakaiSiteId);
		accounts = query.list();
		if (accounts != null && accounts.size() > 0) {
			account = accounts.get(0);
		}
		HibernateUtil.closeSession();
		return account;
	}

	/**
	 * Gets the account by sakai site id.
	 * 
	 * @param sakaiUserId
	 *            the sakai user id
	 * 
	 * @return the account by sakai site id
	 * 
	 */
	private SmsAccount getAccountBySakaiUserId(String sakaiUserId) {
		if (sakaiUserId == null || sakaiUserId.trim().equals("")) {
			return null;
		}
		SmsAccount account = null;
		StringBuilder hql = new StringBuilder();
		hql
				.append(" from SmsAccount account where account.enddate is null or account.enddate > :today ");
		hql.append(" and account.sakaiUserId = :sakaiUserId");
		List<SmsAccount> accounts = new ArrayList<SmsAccount>();
		Session s = HibernateUtil.getSession();
		Query query = s.createQuery(hql.toString());
		query.setDate("today", new Date());
		query.setParameter("sakaiUserId", sakaiUserId);
		accounts = query.list();
		if (accounts != null && accounts.size() > 0) {
			account = accounts.get(0);
		}
		HibernateUtil.closeSession();
		return account;
	}

	/**
	 * Recalculate balance for a specific account.
	 * 
	 * @param accountId
	 *            the account id
	 * @param account
	 *            the account
	 */
	public void recalculateAccountBalance(Long accountId, SmsAccount account) {
		// Use account instead of id?
		if (account == null) {
			account = HibernateLogicFactory.getAccountLogic().getSmsAccount(
					accountId);
		}

		List<SmsTransaction> transactions = HibernateLogicFactory
				.getTransactionLogic().getSmsTransactionsForAccountId(
						account.getId());

		// Sort by transaction date
		Collections.sort(transactions, new Comparator<SmsTransaction>() {

			public int compare(SmsTransaction arg0, SmsTransaction arg1) {
				// TODO Auto-generated method stub
				return arg0.getTransactionDate().compareTo(
						arg1.getTransactionDate());
			}

		});

		// Calculate balance
		Float balance = 0.0F;
		for (SmsTransaction transaction : transactions) {
			balance += transaction.getTransactionAmount();
		}
		account.setBalance(balance);
		persistSmsAccount(account);
	}
}
