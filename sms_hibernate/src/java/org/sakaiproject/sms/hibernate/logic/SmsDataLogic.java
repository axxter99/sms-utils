/***********************************************************************************
 * SmsDataLogic.java
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

package org.sakaiproject.sms.hibernate.logic;

import java.util.List;
import java.util.Set;

import org.sakaiproject.sms.hibernate.model.*;

/**
 * The data service will handle all database transactions for the sms tool in
 * Sakai.
 * 
 * @author louis@psybergate.com
 * @version 1.0
 * @created 12-Nov-2008 08:12:41 AM
 */
public interface SmsDataLogic {

	public SmsAccount getSmsAccount(Long smsAccountID);

	// crud
	public SmsConfig getSmsConfig();

	// must look up smsTask and populate
	public SmsMessage getSmsMessage(Long smsMessageID);

	public Set<SmsMessage> getSmsMessages(Long smsTaskID);

	// crud
	public SmsTask getSmsTask(Long smsTaskID);

	// statusCode may be null
	public Set<SmsTask> getSmsTasks(String sakaiSiteID, String statusCode);

	public SmsTransaction getSmsTransaction(Long smsTransactionID);

	public Set<SmsTransaction> getSmsTransactions(Long smsAccountID);
}