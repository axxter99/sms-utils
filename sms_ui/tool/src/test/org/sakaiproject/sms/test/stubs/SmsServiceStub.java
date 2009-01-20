/***********************************************************************************
 * SmsServiceStub.java
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
package org.sakaiproject.sms.test.stubs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.sakaiproject.sms.api.SmsService;
import org.sakaiproject.sms.hibernate.model.SmsTask;

public class SmsServiceStub implements SmsService {

	public boolean calculateCalled = false;
	public boolean sufficientCredits = false;

	/**
	 * Sets boolean to check if method called
	 */
	public SmsTask calculateEstimatedGroupSize(SmsTask arg0) {
		arg0.setCreditEstimate(2);
		calculateCalled = true;
		return arg0;
	}

	public boolean checkSufficientCredits(String arg0, String arg1, int arg2) {
		return sufficientCredits;
	}

	public SmsTask getPreliminaryTask(Set<String> arg0, Date arg1, String arg2,
			String arg3, String arg4, String arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsTask getPreliminaryTask(String arg0, Date arg1, String arg2,
			String arg3, String arg4, String arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> validateTask(SmsTask arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsTask getPreliminaryTask(Date arg0, String arg1, String arg2,
			String arg3, String arg4, Set<String> arg5) {
		// TODO Auto-generated method stub
		return null;
	}

	public SmsTask getPreliminaryTask(Date arg0, String arg1, String arg2,
			String arg3, String arg4, List<String> arg5) {
		// TODO Auto-generated method stub
		return null;
	}

}
