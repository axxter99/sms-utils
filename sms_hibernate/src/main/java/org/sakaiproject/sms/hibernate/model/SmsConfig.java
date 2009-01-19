/***********************************************************************************
 * SmsConfig.java
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

// TODO: Auto-generated Javadoc
/**
 * Some site specific configuration info for the sms tool. Configuration info
 * could also be set up for a specific tool in a specific site or sytem whide by
 * leaving some fields empty.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 19-Nov-2008
 */
public class SmsConfig extends BaseModel {

	/**
	 * Person(s) who will receive notifications regarding transactions and sms
	 * delivery reports. A comma list is allowed.
	 */
	private String notificationEmail;

	/** The sakai site id (e.g. !admin) */
	private String sakaiSiteId;

	/** The sakai tool id, if empty, the config is for the site. */
	private String sakaiToolId;

	/** Enable or disable sms functionality for the site or tool. */
	private String smsEnabled;

	/**
	 * The number of times to retry to send the message to gateway. Retries will
	 * occur when the gateway could not be contacted or when the gateway bind
	 * failed.
	 */
	private Integer smsRetryMaxCount;

	/** The sms retry schedule interval. */
	private Integer smsRetryScheduleInterval;

	/** The duration in seconds for a task to be valid since in date to send. */
	private Integer smsTaskMaxLifeTime;

	/** The notification email billing. */
	private String notificationEmailBilling;

	/** The notification email sent. */
	private String notificationEmailSent;

	/** Number of lines to show in grid windows. */
	private Integer pagingSize;

	/** Sets the interval for the scheduler. */
	private Integer schedulerInterval = 60000;

	/**
	 * The maximum amount of minutes to wait for a delivery report for each
	 * message. If a message exceeds this time, it will be marked as failed.
	 */
	private Integer delReportTimeoutDuration;

	/** The cost of one credit */
	private Float creditCost;

	/**
	 * Instantiates a new sms configuration.
	 */
	public SmsConfig() {

	}

	/**
	 * Gets the notification email.
	 * 
	 * @return the notification email
	 */
	public String getNotificationEmail() {
		return notificationEmail;
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
	 * Gets the sms enabled.
	 * <p>
	 * NB: Used only by Hibernate
	 * 
	 * @return the sms enabled
	 */
	private String getSmsEnabled() {
		return smsEnabled;
	}

	/**
	 * Sets the notification email.
	 * 
	 * @param notificationEmail
	 *            the new notification email
	 */
	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
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
	 * Sets sms enabled/disabled.
	 * <p>
	 * NB: Used only by Hibernate
	 * 
	 * @param smsEnabled
	 *            the new sms enabled/disabled
	 */
	private void setSmsEnabled(String smsEnabled) {
		this.smsEnabled = smsEnabled;
	}

	/**
	 * Sets the sms enabled.
	 * 
	 * @param smsEnabled
	 *            the new sms enabled
	 */
	public void setSendSmsEnabled(Boolean smsEnabled) {
		this.smsEnabled = smsEnabled ? "1" : "0";
	}

	/**
	 * Gets the sms enabled.
	 * 
	 * @return the sms enabled
	 */
	public Boolean isSendSmsEnabled() {
		if (this.smsEnabled != null) {
			return this.smsEnabled.equals("1") ? true : false;
		}
		return null;
	}

	/**
	 * Gets the sms retry max count.
	 * 
	 * @return the sms retry max count
	 */
	public Integer getSmsRetryMaxCount() {
		return smsRetryMaxCount;
	}

	/**
	 * Sets the sms retry max count.
	 * 
	 * @param smsRetryMaxCount
	 *            the new sms retry max count
	 */
	public void setSmsRetryMaxCount(Integer smsRetryMaxCount) {
		this.smsRetryMaxCount = smsRetryMaxCount;
	}

	/**
	 * Gets the sms retry schedule interval.
	 * 
	 * @return the sms retry schedule interval
	 */
	public Integer getSmsRetryScheduleInterval() {
		return smsRetryScheduleInterval;
	}

	/**
	 * Sets the sms retry schedule interval.
	 * 
	 * @param smsRetryScheduleInterval
	 *            the new sms retry schedule interval
	 */
	public void setSmsRetryScheduleInterval(Integer smsRetryScheduleInterval) {
		this.smsRetryScheduleInterval = smsRetryScheduleInterval;
	}

	/**
	 * Gets the sms task max life time.
	 * 
	 * @return the sms task max life time
	 */
	public Integer getSmsTaskMaxLifeTime() {
		return smsTaskMaxLifeTime;
	}

	/**
	 * Sets the sms task max life time.
	 * 
	 * @param smsTaskMaxLifeTime
	 *            the new sms task max life time
	 */
	public void setSmsTaskMaxLifeTime(Integer smsTaskMaxLifeTime) {
		this.smsTaskMaxLifeTime = smsTaskMaxLifeTime;
	}

	/**
	 * Gets the notification email billing.
	 * 
	 * @return the notification email billing
	 */
	public String getNotificationEmailBilling() {
		return notificationEmailBilling;
	}

	/**
	 * Sets the notification email billing.
	 * 
	 * @param notificationEmailBilling
	 *            the new notification email billing
	 */
	public void setNotificationEmailBilling(String notificationEmailBilling) {
		this.notificationEmailBilling = notificationEmailBilling;
	}

	/**
	 * Gets the notification email sent.
	 * 
	 * @return the notification email sent
	 */
	public String getNotificationEmailSent() {
		return notificationEmailSent;
	}

	/**
	 * Sets the notification email sent.
	 * 
	 * @param notificationEmailSent
	 *            the new notification email sent
	 */
	public void setNotificationEmailSent(String notificationEmailSent) {
		this.notificationEmailSent = notificationEmailSent;
	}

	/**
	 * Gets the paging size.
	 * 
	 * @return the paging size
	 */
	public Integer getPagingSize() {
		return pagingSize;
	}

	/**
	 * Sets the paging size.
	 * 
	 * @param pagingSize
	 *            the new paging size
	 */
	public void setPagingSize(Integer pagingSize) {
		this.pagingSize = pagingSize;
	}

	public Integer getDelReportTimeoutDuration() {
		return delReportTimeoutDuration;
	}

	public void setDelReportTimeoutDuration(Integer delReportTimeoutDuration) {
		this.delReportTimeoutDuration = delReportTimeoutDuration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((delReportTimeoutDuration == null) ? 0
						: delReportTimeoutDuration.hashCode());
		result = prime
				* result
				+ ((notificationEmail == null) ? 0 : notificationEmail
						.hashCode());
		result = prime
				* result
				+ ((notificationEmailBilling == null) ? 0
						: notificationEmailBilling.hashCode());
		result = prime
				* result
				+ ((notificationEmailSent == null) ? 0 : notificationEmailSent
						.hashCode());
		result = prime * result
				+ ((pagingSize == null) ? 0 : pagingSize.hashCode());
		result = prime * result
				+ ((sakaiSiteId == null) ? 0 : sakaiSiteId.hashCode());
		result = prime * result
				+ ((sakaiToolId == null) ? 0 : sakaiToolId.hashCode());
		result = prime * result
				+ ((smsEnabled == null) ? 0 : smsEnabled.hashCode());
		result = prime
				* result
				+ ((smsRetryMaxCount == null) ? 0 : smsRetryMaxCount.hashCode());
		result = prime
				* result
				+ ((smsRetryScheduleInterval == null) ? 0
						: smsRetryScheduleInterval.hashCode());
		result = prime
				* result
				+ ((smsTaskMaxLifeTime == null) ? 0 : smsTaskMaxLifeTime
						.hashCode());
		result = prime * result
				+ ((creditCost == null) ? 0 : creditCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SmsConfig))
			return false;
		SmsConfig other = (SmsConfig) obj;
		if (delReportTimeoutDuration == null) {
			if (other.delReportTimeoutDuration != null)
				return false;
		} else if (!delReportTimeoutDuration
				.equals(other.delReportTimeoutDuration))
			return false;
		if (notificationEmail == null) {
			if (other.notificationEmail != null)
				return false;
		} else if (!notificationEmail.equals(other.notificationEmail))
			return false;
		if (notificationEmailBilling == null) {
			if (other.notificationEmailBilling != null)
				return false;
		} else if (!notificationEmailBilling
				.equals(other.notificationEmailBilling))
			return false;
		if (notificationEmailSent == null) {
			if (other.notificationEmailSent != null)
				return false;
		} else if (!notificationEmailSent.equals(other.notificationEmailSent))
			return false;
		if (pagingSize == null) {
			if (other.pagingSize != null)
				return false;
		} else if (!pagingSize.equals(other.pagingSize))
			return false;
		if (sakaiSiteId == null) {
			if (other.sakaiSiteId != null)
				return false;
		} else if (!sakaiSiteId.equals(other.sakaiSiteId))
			return false;
		if (sakaiToolId == null) {
			if (other.sakaiToolId != null)
				return false;
		} else if (!sakaiToolId.equals(other.sakaiToolId))
			return false;
		if (smsEnabled == null) {
			if (other.smsEnabled != null)
				return false;
		} else if (!smsEnabled.equals(other.smsEnabled))
			return false;
		if (smsRetryMaxCount == null) {
			if (other.smsRetryMaxCount != null)
				return false;
		} else if (!smsRetryMaxCount.equals(other.smsRetryMaxCount))
			return false;
		if (smsRetryScheduleInterval == null) {
			if (other.smsRetryScheduleInterval != null)
				return false;
		} else if (!smsRetryScheduleInterval
				.equals(other.smsRetryScheduleInterval))
			return false;
		if (smsTaskMaxLifeTime == null) {
			if (other.smsTaskMaxLifeTime != null)
				return false;
		} else if (!smsTaskMaxLifeTime.equals(other.smsTaskMaxLifeTime))
			return false;
		if (creditCost == null) {
			if (other.creditCost != null)
				return false;
		} else if (!creditCost.equals(other.creditCost))
			return false;
		return true;
	}

	public void setSchedulerInterval(Integer schedulerInterval) {
		this.schedulerInterval = schedulerInterval;
	}

	public Integer getSchedulerInterval() {
		return schedulerInterval;
	}

	/**
	 * Gets the credit cost.
	 * 
	 * @return the credit cost
	 */
	public Float getCreditCost() {
		return creditCost;
	}

	/**
	 * Sets the credit cost.
	 * 
	 * @param creditCost
	 *            the new credit cost
	 */
	public void setCreditCost(Float creditCost) {
		this.creditCost = creditCost;
	}

}
