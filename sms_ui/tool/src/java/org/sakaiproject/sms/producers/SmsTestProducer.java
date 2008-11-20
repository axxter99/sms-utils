/***********************************************************************************
 * SmsTestProducer.java
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
package org.sakaiproject.sms.producers;

import static org.sakaiproject.sms.constants.SMSConstants.MAX_SMS_LENGTH;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.decorators.UIDisabledDecorator;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;

public class SmsTestProducer implements ViewComponentProducer, DefaultView {
	public static final String VIEW_ID = "sms_test";

	public String getViewID() {
		return VIEW_ID;
	}

    /**
     * @see ComponentProducer#fillComponents(UIContainer, ViewParameters, ComponentChecker)
     */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {
			String smsTestBean = "SmsTestBean";	
			
			UIMessage.make(tofill, "page-title", "sms.test.title");
			UIForm form = UIForm.make(tofill, "test-form");
			
			UIMessage.make(form,"mobile-nr-label", "sms.test.mobile-number");
			UIInput.make(form, "mobile-nr", smsTestBean + ".mobileNumber");
			
			UIMessage.make(form,"message-body-label", "sms.test.message-body");
			UIInput messageBody = UIInput.make(form, "message-body", smsTestBean + ".messageBody");
			
			UIMessage.make(form,"chars-remaining-label" , "sms.test.chars-remaining");
			UIInput charsRemaining = UIInput.make(form, "chars-remaining", null, Integer.toString(MAX_SMS_LENGTH));
			// Disables the characters remaining input
			charsRemaining.decorate(new UIDisabledDecorator());
			
			UICommand.make(form,"ok-button", UIMessage.make("sms.general.ok"), "SmsTestActionBean.send");
			UICommand.make(form,"cancel-button", UIMessage.make("sms.general.cancel"), null);
			
			UIMessage.make(form,"smpp-debug-label", "sms.test.smpp-debug");
			UIInput.make(form, "smpp-debug", null);
			
			UIInitBlock.make(tofill, "init-msg-body-change", "initMsgBodyChange", new Object[] { messageBody, charsRemaining,  Integer.toString(MAX_SMS_LENGTH)});
	}

}