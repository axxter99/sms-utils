/**********************************************************************************
 * $URL:$
 * $Id:$
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009 The Sakai Foundation
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
package org.sakaiproject.sms.model.hibernate;

import java.util.Date;

import org.sakaiproject.sms.model.hibernate.constants.SmsConst_DeliveryStatus;
import org.sakaiproject.sms.util.DateUtil;

/**
 * A single sms message. One or more messages is linked to a sms task. When a
 * task is future dated (e.q.: send message x to group x and date z). It's
 * messages will only be inserted on the date the task must execute.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 26-Nov-2008
 */
public class SmsMessage extends BaseModel {

	/** The date-time when the message was queued. */
	private Date dateQueued;
	
	/** The date-time when the message was sent to the gateway. */
	private Date dateSent;

	/** The date-time when the message was delivered. */
	private Date dateDelivered;

	/** Info for debugging purposes. */
	private String debugInfo;

	/** The reason for a message failing */
	private String failReason;

	/** Unique mobile number to send the message to. */
	private String mobileNumber;

	/**
	 * The sakai user that will receive the message. May be empty in the case of
	 * sending ad-hoc messages.
	 */
	private String sakaiUserId;

	/**
	 * The smsc (sms centre) message id. A unique message id generated by the
	 * message centre. The gateway delivery report will be linked back to the
	 * message using this id.
	 */
	private String smscMessageId;

	/** The sms task linked to this message. */
	private SmsTask smsTask;

	/** Current delivery status of this message. */
	private String statusCode;

	/**
	 * The submit result returned by the gateway when the message was sent.
	 * (This is not the delivery report.)
	 */
	private boolean submitResult;

	/**
	 * The status code as received in the sms delivery report from the gateway.
	 * See SmsConst_SmscDeliveryStatus.java for a list of possible codes.
	 */
	private Integer smscDeliveryStatusCode;

	/**
	 * A unique id for the specific gateway. When you switch to a new gateway,
	 * this number must be incremented in smpp.properties.
	 */
	private String smscId;

	/**
	 * Instantiates a new sms message.
	 */
	public SmsMessage() {
		super();
		this.statusCode = SmsConst_DeliveryStatus.STATUS_PENDING;
		this.setDateQueued(new Date());

	}

	/**
	 * Instantiates a new sms message. For testing of SMPP service.
	 * 
	 * @param mobileNumber
	 *            the mobile number
	 * @param messageBody
	 *            the message body
	 * @depricated Do not use
	 */
	public SmsMessage(String mobileNumber) {
		super();
		this.mobileNumber = mobileNumber;
		this.statusCode = SmsConst_DeliveryStatus.STATUS_PENDING;

	}

	/**
	 * Gets the date delivered.
	 * 
	 * @return the date delivered
	 */
	public Date getDateDelivered() {
		return dateDelivered;
	}

	public Date getDateQueued() {
		return dateQueued;
	}

	public void setDateQueued(Date dateQueued) {
		this.dateQueued = dateQueued;
	}
	
	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	
	/**
	 * Gets the debug info.
	 * 
	 * @return the debug info
	 */
	public String getDebugInfo() {
		return debugInfo;
	}

	/**
	 * Gets the message body.
	 * 
	 * @return the message body
	 */
	public String getMessageBody() {
		return smsTask.getMessageBody();
	}

	/**
	 * Gets the message reply body.
	 * 
	 * @return the message body
	 */
	public String getMessageReplyBody() {
		return smsTask.getMessageReplyBody();
	}

	/**
	 * Gets the mobile number.
	 * 
	 * @return the mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
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
	 * Gets the smsc Message id.
	 * 
	 * @return the smsc Message id
	 */
	public String getSmscMessageId() {
		return smscMessageId;
	}

	/**
	 * Gets the sms task.
	 * 
	 * @return the sms task
	 */
	public SmsTask getSmsTask() {
		return smsTask;
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
	 * Checks if submit to gateway was successful.
	 * 
	 * @return true, if is submit result
	 */
	public boolean isSubmitResult() {
		return submitResult;
	}

	/**
	 * Sets the date delivered.
	 * 
	 * @param dateDelivered
	 *            the new date delivered
	 */
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = DateUtil.getUsableDate(dateDelivered);
	}

	/**
	 * Sets the debug info.
	 * 
	 * @param debugInfo
	 *            the new debug info
	 */
	public void setDebugInfo(String debugInfo) {
		if (debugInfo == null) {
			this.debugInfo = "";
		} else {
			this.debugInfo += debugInfo + "\n";
		}
	}

	/**
	 * Sets the message body.
	 * 
	 * @return the message body
	 */
	public void setMessageBody(String messageBody) {
		smsTask.setMessageBody(messageBody);
	}

	/**
	 * Sets the message body.
	 * 
	 * @return the message reply body
	 */
	public void setMessageReplyBody(String messageReplyBody) {
		smsTask.setMessageReplyBody(messageReplyBody);
	}

	/**
	 * Sets the mobile number.
	 * 
	 * @param mobileNumber
	 *            the new mobile number
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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
	 * Sets the smsc id.
	 * 
	 * @param smscMessageId
	 *            the new smsc Message id
	 */
	public void setSmscMessageId(String smscId) {
		this.smscMessageId = smscId;
	}

	/**
	 * Sets the sms task.
	 * 
	 * @param smsTask
	 *            the new sms task
	 */
	public void setSmsTask(SmsTask smsTask) {
		this.smsTask = smsTask;
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

	/**
	 * Sets the submit result.
	 * 
	 * @param submitResult
	 *            the new submit result
	 */
	public void setSubmitResult(boolean submitResult) {
		this.submitResult = submitResult;
	}

	/**
	 * @return the smscDeliveryStatusCode
	 */
	public Integer getSmscDeliveryStatusCode() {
		return smscDeliveryStatusCode;
	}

	/**
	 * @param smscDeliveryStatusCode
	 *            the smscDeliveryStatusCode to set
	 */
	public void setSmscDeliveryStatusCode(Integer smscDeliveryStatusCode) {
		this.smscDeliveryStatusCode = smscDeliveryStatusCode;
	}

	/**
	 * Gets the smsc id.
	 * 
	 * @return the smsc id
	 */
	public String getSmscId() {
		return smscId;
	}

	/**
	 * Sets the smsc id.
	 * 
	 * @param smscId
	 *            the new smsc id
	 */
	public void setSmscId(String smscId) {
		this.smscId = smscId;
	}

	/**
	 * Gets the fail reason
	 * 
	 * @return
	 */
	public String getFailReason() {
		return failReason;
	}

	/**
	 * Sets the fail reason
	 * 
	 * @param failReason
	 */
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		// int result = super.hashCode();
		int result = 43;
		result = prime * result
				+ ((debugInfo == null) ? 0 : debugInfo.hashCode());
		result = prime * result
				+ ((dateDelivered == null) ? 0 : dateDelivered.hashCode());
		result = prime * result
				+ ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result
				+ ((sakaiUserId == null) ? 0 : sakaiUserId.hashCode());
		result = prime
				* result
				+ ((smscDeliveryStatusCode == null) ? 0
						: smscDeliveryStatusCode.hashCode());
		result = prime * result + ((smscId == null) ? 0 : smscId.hashCode());
		result = prime * result
				+ ((smscMessageId == null) ? 0 : smscMessageId.hashCode());
		result = prime * result
				+ ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + (submitResult ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SmsMessage))
			return false;
		SmsMessage other = (SmsMessage) obj;
		if (debugInfo == null) {
			if (other.debugInfo != null)
				return false;
		} else if (!debugInfo.equals(other.debugInfo))
			return false;
		if (dateDelivered == null) {
			if (other.dateDelivered != null)
				return false;
		} else if (!dateDelivered.equals(other.dateDelivered))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (sakaiUserId == null) {
			if (other.sakaiUserId != null)
				return false;
		} else if (!sakaiUserId.equals(other.sakaiUserId))
			return false;
		if (smscDeliveryStatusCode == null) {
			if (other.smscDeliveryStatusCode != null)
				return false;
		} else if (!smscDeliveryStatusCode.equals(other.smscDeliveryStatusCode))
			return false;
		if (smscId == null) {
			if (other.smscId != null)
				return false;
		} else if (!smscId.equals(other.smscId))
			return false;
		if (smscMessageId == null) {
			if (other.smscMessageId != null)
				return false;
		} else if (!smscMessageId.equals(other.smscMessageId))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		if (submitResult != other.submitResult)
			return false;
		return true;
	}

}
