/***********************************************************************************
 * SmsTask.java
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
import java.util.Set;

import org.sakaiproject.sms.hibernate.model.constants.SmsConst_TaskDeliveryStatus;

/**
 * The object to represent a sms task that needs to be processed. For example:
 * send message A to sakai group B at time C. When a sms task is processed, a
 * record is inserted into SMS_MESSAGE for each message that must be sent out.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 19-Nov-2008
 */
public class SmsTask extends AbstractBaseModel {

	/**
	 * Approximate credit cost for this task. The exact credits can only be
	 * calculated when the task is processed (might be in the future).
	 */
	private Integer creditEstimate;

	/** The date created. */
	private Timestamp dateCreated;

	/** The date processed. */
	private Timestamp dateProcessed;

	/** Post dated for future delivery, deliver asap if equal to DateCreated. */
	private Timestamp dateToSend;

	/** The Sakai group who will receive the message, empty if not applicable. */
	private String deliveryGroupId;

	/** The friendly name of the Sakai group. */
	private String deliveryGroupName;

	/**
	 * Will be used for incoming messages.
	 */
	private String deliveryUserId;

	/** The group size actual. */
	private Integer groupSizeActual;

	/** The group size estimate. */
	private Integer groupSizeEstimate;

	// Actual SMS contents
	/** The message body. */
	private String messageBody;

	// 
	/** Type of task, only SO (system originating) for now. */
	private Integer messageTypeId;

	/** Number of delivery attempts. */
	private Integer retryCount;

	/** The sakai site from where the sms task originated. */
	private String sakaiSiteId;

	/** The sakai tool id from where the sms task originated. */
	private String sakaiToolId;

	/** The sakai tool name from where the sms task originated. */
	private String sakaiToolName;

	/** The Sakai user name of the sender. */
	private String senderUserName;

	// 
	/** The sms account (cost centre) that will pay for the messages. */
	private Integer smsAccountId;

	/** The sms messages. */
	private Set<SmsMessage> smsMessages;

	/** Current status of this task. */
	private String statusCode;

	/**
	 * Instantiates a new sms task.
	 */
	public SmsTask() {
	}

	/**
	 * Instantiates a new sms task.
	 * 
	 * @param sakaiSiteID
	 *            the sakai site id
	 * @param deliveryUserID
	 *            the delivery user id
	 * @param deliveryGroupID
	 *            the delivery group id
	 * @param accountID
	 *            the account id
	 * @param messageBody
	 *            the message body
	 */
	public SmsTask(String sakaiSiteID, String deliveryUserID,
			String deliveryGroupID, int accountID, String messageBody) {
		this.sakaiSiteId = sakaiSiteID;
		this.deliveryUserId = deliveryUserID;
		this.deliveryGroupId = deliveryGroupID;
		this.smsAccountId = accountID;
		this.messageBody = messageBody;
		this.retryCount = 0;
		this.statusCode = "";
		this.creditEstimate = 0;
		this.statusCode = SmsConst_TaskDeliveryStatus.STATUS_PENDING;

	}

	/**
	 * Gets the credit estimate.
	 * 
	 * @return the credit estimate
	 */
	public Integer getCreditEstimate() {
		return creditEstimate;
	}

	/**
	 * Gets the date created.
	 * 
	 * @return the date created
	 */
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	/**
	 * Gets the date processed.
	 * 
	 * @return the date processed
	 */
	public Timestamp getDateProcessed() {
		return dateProcessed;
	}

	/**
	 * Gets the date to send.
	 * 
	 * @return the date to send
	 */
	public Timestamp getDateToSend() {
		return dateToSend;
	}

	/**
	 * Gets the delivery group id.
	 * 
	 * @return the delivery group id
	 */
	public String getDeliveryGroupId() {
		return deliveryGroupId;
	}

	/**
	 * Gets the delivery group name.
	 * 
	 * @return the delivery group name
	 */
	public String getDeliveryGroupName() {
		return deliveryGroupName;
	}

	/**
	 * Gets the delivery user id.
	 * 
	 * @return the delivery user id
	 */
	public String getDeliveryUserId() {
		return deliveryUserId;
	}

	/**
	 * Gets the group size actual.
	 * 
	 * @return the group size actual
	 */
	public Integer getGroupSizeActual() {
		return groupSizeActual;
	}

	/**
	 * Gets the group size estimate.
	 * 
	 * @return the group size estimate
	 */
	public Integer getGroupSizeEstimate() {
		return groupSizeEstimate;
	}

	/**
	 * Gets the message body.
	 * 
	 * @return the message body
	 */
	public String getMessageBody() {
		return messageBody;
	}

	/**
	 * Gets the message type id.
	 * 
	 * @return the message type id
	 */
	public Integer getMessageTypeId() {
		return messageTypeId;
	}

	/**
	 * Gets the retry count.
	 * 
	 * @return the retry count
	 */
	public Integer getRetryCount() {
		return retryCount;
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
	 * Gets the sakai tool id.
	 * 
	 * @return the sakai tool id
	 */
	public String getSakaiToolId() {
		return sakaiToolId;
	}

	/**
	 * Gets the sakai tool name.
	 * 
	 * @return the sakai tool name
	 */
	public String getSakaiToolName() {
		return sakaiToolName;
	}

	/**
	 * Gets the sender user name.
	 * 
	 * @return the sender user name
	 */
	public String getSenderUserName() {
		return senderUserName;
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
	 * Gets the sms messages.
	 * 
	 * @return the sms messages
	 */
	public Set<SmsMessage> getSmsMessages() {
		return smsMessages;
	}

	/**
	 * Gets the status code.
	 * 
	 * @return the status code
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the credit estimate.
	 * 
	 * @param creditEstimate
	 *            the new credit estimate
	 */
	public void setCreditEstimate(Integer creditEstimate) {
		this.creditEstimate = creditEstimate;
	}

	/**
	 * Sets the date created.
	 * 
	 * @param dateCreated
	 *            the new date created
	 */
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Sets the date processed.
	 * 
	 * @param dateProcessed
	 *            the new date processed
	 */
	public void setDateProcessed(Timestamp dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	/**
	 * Sets the date to send.
	 * 
	 * @param dateToSend
	 *            the new date to send
	 */
	public void setDateToSend(Timestamp dateToSend) {
		this.dateToSend = dateToSend;
	}

	/**
	 * Sets the delivery group id.
	 * 
	 * @param deliveryGroupId
	 *            the new delivery group id
	 */
	public void setDeliveryGroupId(String deliveryGroupId) {
		this.deliveryGroupId = deliveryGroupId;
	}

	/**
	 * Sets the delivery group name.
	 * 
	 * @param deliveryGroupName
	 *            the new delivery group name
	 */
	public void setDeliveryGroupName(String deliveryGroupName) {
		this.deliveryGroupName = deliveryGroupName;
	}

	/**
	 * Sets the delivery user id.
	 * 
	 * @param deliveryUserId
	 *            the new delivery user id
	 */
	public void setDeliveryUserId(String deliveryUserId) {
		this.deliveryUserId = deliveryUserId;
	}

	/**
	 * Sets the group size actual.
	 * 
	 * @param groupSizeActual
	 *            the new group size actual
	 */
	public void setGroupSizeActual(Integer groupSizeActual) {
		this.groupSizeActual = groupSizeActual;
	}

	/**
	 * Sets the group size estimate.
	 * 
	 * @param groupSizeEstimate
	 *            the new group size estimate
	 */
	public void setGroupSizeEstimate(Integer groupSizeEstimate) {
		this.groupSizeEstimate = groupSizeEstimate;
	}

	/**
	 * Sets the message body.
	 * 
	 * @param messageBody
	 *            the new message body
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	/**
	 * Sets the message type id.
	 * 
	 * @param messageTypeId
	 *            the new message type id
	 */
	public void setMessageTypeId(Integer messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	/**
	 * Sets the retry count.
	 * 
	 * @param retryCount
	 *            the new retry count
	 */
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
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
	 * Sets the sakai tool id.
	 * 
	 * @param sakaiToolId
	 *            the new sakai tool id
	 */
	public void setSakaiToolId(String sakaiToolId) {
		this.sakaiToolId = sakaiToolId;
	}

	/**
	 * Sets the sakai tool name.
	 * 
	 * @param sakaiToolName
	 *            the new sakai tool name
	 */
	public void setSakaiToolName(String sakaiToolName) {
		this.sakaiToolName = sakaiToolName;
	}

	/**
	 * Sets the sender user name.
	 * 
	 * @param senderUserName
	 *            the new sender user name
	 */
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
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
	 * Sets the sms messages.
	 * 
	 * @param smsMessages
	 *            the new sms messages
	 */
	public void setSmsMessages(Set<SmsMessage> smsMessages) {
		this.smsMessages = smsMessages;
	}

	/**
	 * Sets the status code.
	 * 
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}