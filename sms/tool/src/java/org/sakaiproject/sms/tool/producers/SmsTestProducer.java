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
package org.sakaiproject.sms.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.sms.model.hibernate.SmsAccount;
import org.sakaiproject.sms.tool.beans.ActionResults;
import org.sakaiproject.sms.tool.otp.SmsMessageLocator;
import org.sakaiproject.sms.tool.util.SmsAccountHelper;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.decorators.UIDisabledDecorator;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ComponentProducer;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;

public class SmsTestProducer implements ViewComponentProducer, DefaultView,
		NavigationCaseReporter {

	private SmsAccountHelper accountHelper;

	public static final String VIEW_ID = "sms_test";

	/**
	 * @see ComponentProducer#fillComponents(UIContainer, ViewParameters,
	 *      ComponentChecker)
	 */
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		String smsMessageOTP = SmsMessageLocator.LOCATOR_NAME + "."
				+ SmsMessageLocator.NEW_1;

		UIMessage.make(tofill, "page-title", "sms.test.title");
		UIMessage.make(tofill, "sms-test-heading", "sms.test.heading");
		UIForm form = UIForm.make(tofill, "test-form");

		UIMessage.make(form, "mobile-nr-label", "sms.test.mobile-number");
		UIInput mobileNr = UIInput.make(form, "mobile-nr", smsMessageOTP
				+ ".mobileNumber");
		mobileNr.mustapply = true; // Force to apply

		UIMessage.make(form, "message-body-label", "sms.helper.message-body");
		UIInput messageBody = UIInput.make(form, "message-body", smsMessageOTP
				+ ".messageBody");
		messageBody.mustapply = true; // Force to apply

		UIMessage.make(form, "chars-remaining-label",
				"sms.helper.chars-remaining");
		UIInput charsRemaining = UIInput.make(form, "chars-remaining", null,
				Integer.toString(MAX_SMS_LENGTH));
		// Disables the characters remaining input
		charsRemaining.decorate(new UIDisabledDecorator());

		UICommand.make(form, "ok-button", UIMessage.make("sms.general.send"),
				"SmsTestActionBean.send");
		UICommand.make(form, "cancel-button",
				UIMessage.make("sms.general.cancel")).setReturn(
				ActionResults.CANCEL);

		UIMessage.make(form, "estimated-group-size-label",
				"sms.helper.estimated-group-size");
		UIInput groupSize = UIInput.make(form, "estimated-group-size",
				smsMessageOTP + ".smsTask.groupSizeEstimate");
		groupSize.decorate(new UIDisabledDecorator());
		groupSize.fossilize = false;

		UIMessage.make(form, "account-balance-label",
				"sms.helper.account-balance");
		SmsAccount account = accountHelper.retrieveAccount(smsMessageOTP
				+ ".smsTask.smsAccountId");
		UIInput accountBalance = UIInput.make(form, "account-balance", null,
				(account == null ? 0f : account.getBalance()) + "");
		accountBalance.decorate(new UIDisabledDecorator());
		accountBalance.fossilize = false;

		UIMessage.make(form, "estimated-cost-label",
				"sms.helper.estimated-cost");
		UIInput estimatedCost = UIInput.make(form, "estimated-cost",
				smsMessageOTP + ".smsTask.creditEstimate");
		estimatedCost.decorate(new UIDisabledDecorator());
		estimatedCost.fossilize = false;

		UIMessage.make(form, "smpp-debug-label", "sms.helper.smpp-debug");
		UIInput debug = UIInput.make(form, "smpp-debug", smsMessageOTP
				+ ".debugInfo");
		debug.decorate(new UIDisabledDecorator());

		UIInitBlock.make(tofill, "init-msg-body-change", "initMsgBodyChange",
				new Object[] { messageBody, charsRemaining,
						Integer.toString(MAX_SMS_LENGTH) });
	}

	public String getViewID() {
		return VIEW_ID;
	}

	/**
	 * @see NavigationCaseReporter#reportNavigationCases()
	 */
	public List reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase(ActionResults.SUCCESS,
				new SimpleViewParameters(SmsTestProducer.VIEW_ID),
				ARIResult.FLOW_ONESTEP));
		list.add(new NavigationCase(ActionResults.CANCEL,
				new SimpleViewParameters(SmsTestProducer.VIEW_ID)));
		return list;
	}

	public void setAccountHelper(SmsAccountHelper accountHelper) {
		this.accountHelper = accountHelper;
	}

}