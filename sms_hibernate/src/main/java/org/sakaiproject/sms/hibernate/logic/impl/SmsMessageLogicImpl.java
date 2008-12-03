/***********************************************************************************
 * SmsMessageLogicImpl.java
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

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sakaiproject.sms.hibernate.bean.SearchFilterBean;
import org.sakaiproject.sms.hibernate.dao.HibernateUtil;
import org.sakaiproject.sms.hibernate.dao.SmsDao;
import org.sakaiproject.sms.hibernate.logic.SmsMessageLogic;
import org.sakaiproject.sms.hibernate.logic.impl.exception.SmsSearchException;
import org.sakaiproject.sms.hibernate.model.SmsMessage;
import org.sakaiproject.sms.hibernate.util.DateUtil;

/**
 * The data service will handle all sms Message database transactions for the
 * sms tool in Sakai.
 * 
 * @author julian@psybergate.com
 * @version 1.0
 * @created 25-Nov-2008
 */
public class SmsMessageLogicImpl extends SmsDao implements SmsMessageLogic {

	/**
	 * Deletes and the given entity from the DB
	 */
	public void deleteSmsMessage(SmsMessage smsMessage) {
		delete(smsMessage);
	}

	/**
	 * Gets a SmsMessage entity for the given id
	 * 
	 * @param Long
	 *            sms Message id
	 * @return sms Message
	 */
	public SmsMessage getSmsMessage(Long smsMessageId) {
		return (SmsMessage) findById(SmsMessage.class, smsMessageId);
	}

	/**
	 * Gets all the sms Message records
	 * 
	 * @return List of SmsMessage objects
	 */
	public List<SmsMessage> getAllSmsMessages() {
		Session s = HibernateUtil.currentSession();
		Query query = s.createQuery("from SmsMessage");
		List<SmsMessage> messages = query.list();
		HibernateUtil.closeSession();
		return messages;
	}

	/**
	 * This method will persists the given object.
	 * 
	 * If the object is a new entity then it will be created on the DB. If it is
	 * an existing entity then the record will be updated on the DB.
	 * 
	 * @param sms
	 *            Message to be persisted
	 */
	public void persistSmsMessage(SmsMessage smsMessage) {
		persist(smsMessage);
	}

	/**
	 * Returns a message for the given smsc message id or null if nothing found
	 * 
	 * @param smsc
	 *            message id
	 * @return sms message
	 */
	public SmsMessage getSmsMessageBySmscMessageId(String smscMessageId) {
		Session s = HibernateUtil.currentSession();
		Query query = s
				.createQuery("from SmsMessage mes where mes.smscMessageId = :smscId ");
		query.setParameter("smscId", smscMessageId, Hibernate.STRING);
		List<SmsMessage> messages = query.list();
		HibernateUtil.closeSession();
		if (messages != null && messages.size() > 0) {
			return messages.get(0);
		}
		return null;
	}

	/**
	 * Gets a list of SmsMessage objects for the specified and specified status
	 * code(s).
	 * 
	 * It will ignore the smsTaskId if it is passed as null and return all
	 * smsMessages with the specified status code(s).
	 * 
	 * @param sms
	 *            task id
	 * @param statusCode
	 *            (s)
	 * @return List<SmsMessage> - sms messages
	 */
	public List<SmsMessage> getSmsMessagesWithStatus(Long smsTaskId,
			String... statusCodes) {
		List<SmsMessage> messages = new ArrayList<SmsMessage>();

		// Return empty list if no status codes were passed in
		if (statusCodes.length > 0) {

			StringBuilder hql = new StringBuilder();
			hql.append(" from SmsMessage message where 1=1  ");
			if (smsTaskId != null) {
				hql.append(" and message.smsTask.id = :smsTaskId ");
			}
			hql.append(" and message.statusCode IN (:statusCodes) ");

			log.debug("getSmsTasksFilteredByMessageStatus() HQL: "
					+ hql.toString());
			Query query = HibernateUtil.currentSession().createQuery(
					hql.toString());
			query
					.setParameterList("statusCodes", statusCodes,
							Hibernate.STRING);
			if (smsTaskId != null) {
				query.setParameter("smsTaskId", smsTaskId);
			}
			messages = query.list();
			HibernateUtil.closeSession();

		}
		return messages;
	}

	/**
	 * Gets a list of SmsMessage objects for the specified search criteria
	 * 
	 * @param search
	 *            Bean containing the search criteria
	 * @return LList of SmsMessages
	 * @throws SmsSearchException
	 *             when an invalid search criteria is specified
	 */
	public List<SmsMessage> getSmsMessagesForCriteria(
			SearchFilterBean searchBean) throws SmsSearchException {

		Criteria crit = HibernateUtil.currentSession().createCriteria(
				SmsMessage.class).createAlias("smsTask", "smsTask");

		List<SmsMessage> messages = new ArrayList<SmsMessage>();
		try {
			// Message status
			if (searchBean.getStatus() != null
					&& !searchBean.getStatus().trim().equals("")) {
				crit.add(Restrictions.ilike("statusCode", searchBean
						.getStatus()));
			}

			// Sakai tool name
			if (searchBean.getToolName() != null
					&& !searchBean.getToolName().trim().equals("")) {
				crit.add(Restrictions.ilike("smsTask.sakaiToolName", searchBean
						.getToolName()));
			}

			// Date to send start
			if (searchBean.getDateFrom() != null
					&& !searchBean.getDateFrom().trim().equals("")) {
				Timestamp date = DateUtil
						.getTimestampFromStartDateString(searchBean
								.getDateFrom());
				crit.add(Restrictions.ge("smsTask.dateToSend", date));
			}

			// Date to send end
			if (searchBean.getDateTo() != null
					&& !searchBean.getDateTo().trim().equals("")) {
				Timestamp date = DateUtil
						.getTimestampFromEndDateString(searchBean.getDateTo());
				crit.add(Restrictions.le("smsTask.dateToSend", date));
			}

			// Sender name
			if (searchBean.getSender() != null
					&& !searchBean.getSender().trim().equals("")) {
				crit.add(Restrictions.ilike("smsTask.senderUserName",
						searchBean.getSender()));
			}

			// Mobile number
			if (searchBean.getMobileNumber() != null
					&& !searchBean.getMobileNumber().trim().equals("")) {
				crit.add(Restrictions.ilike("mobileNumber", searchBean
						.getMobileNumber()));
			}

			// Ordering
			if (searchBean.getOrderBy() != null
					&& !searchBean.getOrderBy().trim().equals("")) {
				crit.addOrder((searchBean.sortAsc() ? Order.asc(searchBean
						.getOrderBy()) : Order.desc(searchBean.getOrderBy())));
			}

		} catch (ParseException e) {
			throw new SmsSearchException(e);
		} catch (Exception e) {
			throw new SmsSearchException(e);
		}

		messages = crit.list();
		HibernateUtil.closeSession();
		return messages;
	}

}