/***********************************************************************************
 * SmsAccountValidator.java
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

import org.sakaiproject.sms.hibernate.model.SmsAccount;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SmsAccountValidator implements Validator {

	private boolean isTooLong(String field, int max) {
		if (field != null) {
			if (field.length() > max) {
				return true;
			}
		}
		return false;
	}

	public boolean supports(Class clazz) {
		if (SmsAccount.class.equals(clazz.getClass())) {
			return true;
		}
		return false;
	}

	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "accountName",
				"sms.errors.accountName.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "sakaiSiteId",
				"sms.errors.sakaiSiteId.empty");

		// Because SmsCustomNumberEditor sets all invalid number values as null
		// we must give generic message for null values
		ValidationUtils.rejectIfEmpty(err, "overdraftLimit",
				"sms.errors.overdraftLimit.invalid");
		ValidationUtils.rejectIfEmpty(err, "balance",
				"sms.errors.balance.invalid");

		SmsAccount smsAccount = (SmsAccount) obj;

		if (isTooLong(smsAccount.getAccountName(), 99)) {
			err.rejectValue("accountName", "sms.errors.accountName.tooLong");
		}

		if (isTooLong(smsAccount.getSakaiSiteId(), 99)) {
			err.rejectValue("sakaiSiteId", "sms.errors.sakaiSiteId.tooLong");
		}

		if (isTooLong(smsAccount.getSakaiUserId(), 99)) {
			err.rejectValue("sakaiUserId", "sms.errors.sakaiUserId.tooLong");
		}
	}
}
