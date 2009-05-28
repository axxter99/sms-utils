/***********************************************************************************
 * SmsMessageLogicStub.java
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
package org.sakaiproject.sms.tool.test.stubs;

import java.sql.Timestamp;
import java.util.List;

import org.sakaiproject.sms.bean.SearchFilterBean;
import org.sakaiproject.sms.bean.SearchResultContainer;
import org.sakaiproject.sms.logic.hibernate.SmsMessageLogic;
import org.sakaiproject.sms.logic.hibernate.exception.SmsSearchException;
import org.sakaiproject.sms.model.hibernate.SmsMessage;
import org.sakaiproject.sms.model.hibernate.SmsTask;
import org.sakaiproject.sms.model.hibernate.constants.SmsConst_DeliveryStatus;
import org.sakaiproject.sms.model.hibernate.constants.SmsConstants;

public class SmsMessageLogicStub implements SmsMessageLogic {

	public void deleteSmsMessage(SmsMessage arg0) {
		// TODO Auto-generated method stub

	}

	public List<SmsMessage> getAllSmsMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SmsMessage> getAllSmsMessagesForCriteria(SearchFilterBean arg0)
			throws SmsSearchException {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getBillableMessagesCount(String arg0, String arg1,
			String arg2, Integer arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsMessage getNewTestSmsMessageInstance(String mobileNumber,
			String messageBody) {
		SmsTask smsTask = new SmsTask();
		smsTask.setSakaiSiteId(SmsConstants.SMS_DEV_DEFAULT_SAKAI_SITE_ID);
		smsTask.setSmsAccountId(1l);
		smsTask.setDateCreated(new Timestamp(System.currentTimeMillis()));
		smsTask.setDateToSend(new Timestamp(System.currentTimeMillis()));
		smsTask.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);
		smsTask.setAttemptCount(2);
		smsTask.setMessageBody(SmsConstants.SMS_DEV_DEFAULT_SMS_MESSAGE_BODY);
		smsTask.setSenderUserName(SmsConstants.SMS_DEV_DEFAULT_SAKAI_USER_ID);

		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setSmsTask(smsTask);
		smsMessage.setMobileNumber(mobileNumber);
		smsMessage.setMessageBody(messageBody);
		smsMessage.setSmscMessageId("smscMessageId");
		smsMessage.setSakaiUserId("sakaiUserId");
		smsMessage.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);

		return smsMessage;
	}

	public SearchResultContainer<SmsMessage> getPagedSmsMessagesForCriteria(
			SearchFilterBean arg0) throws SmsSearchException {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsMessage getSmsMessage(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsMessage getSmsMessageBySmscMessageId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsMessage getSmsMessageBySmscMessageId(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public SearchResultContainer<SmsMessage> getSmsMessagesForCriteria(
			SearchFilterBean arg0) throws SmsSearchException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SmsMessage> getSmsMessagesWithStatus(Long arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void persistSmsMessage(SmsMessage arg0) {
		// TODO Auto-generated method stub

	}
}