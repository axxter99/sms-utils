/***********************************************************************************
 * SampleSmsAccountFactory.java
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
package org.sakaiproject.sms.hibernate.test.dataload;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.sms.hibernate.model.SmsAccount;

public class SampleSmsAccountFactory implements Listable {

	private List<SmsAccount> smsAccounts;
	private RandomUtils randomUtils = new RandomUtils();

	public SampleSmsAccountFactory() {
		createSampleSmsAccounts();
	}

	public Object getElementAt(int i) {
		return getTestSmsMessage(i);
	}

	public void refreshList() {
		createSampleSmsAccounts();
	}

	private void createSampleSmsAccounts() {
		smsAccounts = new ArrayList<SmsAccount>();

		SmsAccount smsAccount1 = new SmsAccount(randomUtils
				.getRandomFloat(2000), "S", randomUtils.getRandomFloat(2000),
				"SAK", "BOB001", "Account 1");
		smsAccount1.setAccountEnabled(true);
		smsAccounts.add(smsAccount1);

		SmsAccount smsAccount2 = new SmsAccount(randomUtils
				.getRandomFloat(2000), "P", randomUtils.getRandomFloat(2000),
				"MMM", "JOE001", "Account 1");
		smsAccount2.setAccountEnabled(true);
		smsAccounts.add(smsAccount2);

		SmsAccount smsAccount3 = new SmsAccount(randomUtils
				.getRandomFloat(2000), "R", randomUtils.getRandomFloat(2000),
				"JJT", "MAR001", "Account 1");
		smsAccount3.setAccountEnabled(true);
		smsAccounts.add(smsAccount3);

		SmsAccount smsAccount4 = new SmsAccount(randomUtils
				.getRandomFloat(2000), "F", randomUtils.getRandomFloat(2000),
				"RMLL", "LEE001", "Account 1");
		smsAccount4.setAccountEnabled(true);
		smsAccounts.add(smsAccount4);

		SmsAccount smsAccount5 = new SmsAccount(randomUtils
				.getRandomFloat(2000), "S", randomUtils.getRandomFloat(2000),
				"APG", "POP001", "Account 1");
		smsAccount5.setAccountEnabled(true);
		smsAccounts.add(smsAccount5);
	}

	public List<SmsAccount> getAllTestSmsMessages() {
		return smsAccounts;
	}

	public SmsAccount getTestSmsMessage(int index) {

		if (index >= smsAccounts.size())
			throw new RuntimeException("The specified index is too high");

		return smsAccounts.get(index);
	}

	public int getTotalsmsAccounts() {
		return smsAccounts.size();
	}

}
