/***********************************************************************************
 * HelperProducer.java
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
import org.sakaiproject.sms.model.hibernate.constants.SmsHibernateConstants;
import org.sakaiproject.sms.tool.beans.ActionResults;
import org.sakaiproject.sms.tool.otp.SmsTaskLocator;
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
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;

public class HelperProducer implements ViewComponentProducer,
		NavigationCaseReporter {

	public static final String VIEW_ID = "helper";

	private SmsAccountHelper accountHelper;
	private SmsTaskLocator smsTaskLocator;

	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		String smsTaskOTP = SmsTaskLocator.LOCATOR_NAME + "."
				+ SmsTaskLocator.NEW_1;

		UIMessage.make(tofill, "page-title", "sms.helper.title");
		UIMessage.make(tofill, "sms-helper-heading", "sms.helper.heading");
		UIForm form = UIForm.make(tofill, "helper-form");

		UIMessage.make(form, "message-body-label", "sms.helper.message-body");
		UIInput messageBody = UIInput.make(form, "message-body", smsTaskOTP
				+ ".messageBody");
		messageBody.mustapply = true;

		UIMessage.make(form, "chars-remaining-label",
				"sms.helper.chars-remaining");
		UIInput charsRemaining = UIInput.make(form, "chars-remaining", null,
				Integer.toString(SmsHibernateConstants.MAX_SMS_LENGTH));
		// Disables the characters remaining input
		charsRemaining.decorate(new UIDisabledDecorator());

		if (smsTaskLocator.containsNew()) {
			UICommand.make(form, "action-button", UIMessage
					.make("sms.general.save"), "HelperActionBean.save");
		} else {
			UICommand.make(form, "action-button", UIMessage
					.make("sms.general.continue"),
					"HelperActionBean.doContinue");
		}
		UICommand.make(form, "cancel-button", UIMessage
				.make("sms.general.cancel"), "HelperActionBean.cancel");

		UIMessage.make(form, "estimated-group-size-label",
				"sms.helper.estimated-group-size");
		UIInput groupSize = UIInput.make(form, "estimated-group-size",
				smsTaskOTP + ".groupSizeEstimate");
		groupSize.decorate(new UIDisabledDecorator());
		groupSize.fossilize = false;

		UIMessage.make(form, "account-balance-label",
				"sms.helper.account-balance");
		SmsAccount account = accountHelper.retrieveAccount(smsTaskOTP
				+ ".smsAccountId");
		UIInput accountBalance = UIInput.make(form, "account-balance", null,
				(account == null ? 0f : account.getBalance()) + "");
		accountBalance.decorate(new UIDisabledDecorator());
		accountBalance.fossilize = false;

		UIMessage.make(form, "estimated-cost-label",
				"sms.helper.estimated-cost");
		UIInput estimatedCost = UIInput.make(form, "estimated-cost", smsTaskOTP
				+ ".costEstimate");
		estimatedCost.decorate(new UIDisabledDecorator());
		estimatedCost.fossilize = false;

		UIInitBlock
				.make(
						tofill,
						"init-msg-body-change",
						"initMsgBodyChange",
						new Object[] {
								messageBody,
								charsRemaining,
								Integer
										.toString(SmsHibernateConstants.MAX_SMS_LENGTH) });

	}

	public String getViewID() {
		return VIEW_ID;
	}

	/**
	 * @see NavigationCaseReporter#reportNavigationCases()
	 */
	public List reportNavigationCases() {
		List<NavigationCase> list = new ArrayList<NavigationCase>();
		list.add(new NavigationCase(ActionResults.CANCEL,
				new SimpleViewParameters(HelperProducer.VIEW_ID)));
		list.add(new NavigationCase(ActionResults.ERROR,
				new SimpleViewParameters(HelperProducer.VIEW_ID)));
		list.add(new NavigationCase(ActionResults.CONTINUE,
				new SimpleViewParameters(HelperProducer.VIEW_ID),
				ARIResult.FLOW_FASTSTART));
		list.add(new NavigationCase(ActionResults.SUCCESS,
				new SimpleViewParameters(HelperProducer.VIEW_ID)));
		return list;
	}

	public void setAccountHelper(SmsAccountHelper accountHelper) {
		this.accountHelper = accountHelper;
	}

	public void setSmsTaskLocator(SmsTaskLocator smsTaskLocator) {
		this.smsTaskLocator = smsTaskLocator;
	}

}
