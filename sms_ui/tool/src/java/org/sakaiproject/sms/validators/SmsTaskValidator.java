/***********************************************************************************
 * SmsTaskValidator.java
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
package org.sakaiproject.sms.validators;

import org.sakaiproject.sms.hibernate.model.SmsTask;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SmsTaskValidator implements Validator {

	public boolean supports(Class clazz) {
		if (SmsTask.class.equals(clazz.getClass())) {
			return true;
		}
		return false;
	}

	public void validate(Object obj, Errors err) {
		// TODO: Validations for SmsTask
	}
}
